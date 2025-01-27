package org.example.librarycatalogservice.adapters.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import mu.KLogging
import org.example.librarycatalogservice.adapters.kafka.BookAddedEvent
import org.example.librarycatalogservice.adapters.kafka.BookEventProducer
import org.example.librarycatalogservice.domain.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class RenameBookRequest(@field:NotBlank(message = "New title is required") val newTitle: String)

@RestController
@RequestMapping("/api/v1/books")
class BookEndpoints(private val bookService: BookService, private val bookEventProducer: BookEventProducer) {

    companion object : KLogging()

    @GetMapping
    fun getAll(): List<BookResponse> = bookService.findAll().map{ it.toResponseElement() }

    @GetMapping("/{bookId}")
    fun getById(@PathVariable bookId: String): BookResponse = bookService.findById(bookId).toResponseElement()

    @GetMapping("/search")
    fun getByTitle(@RequestParam(required=true) title: String): BookResponse {
        logger.info { "Searching for $title" }
        return bookService.findByTitle(title).toResponseElement()
    }

    @PostMapping
    fun addNew(@Valid @RequestBody request: AddBookRequest): ResponseEntity<Void> {
        val bookId = UUID.randomUUID().toString()
        val newBook = request.toDomain(bookId)
        bookService.add(newBook)
        bookEventProducer.sendBookAddedEvent(BookAddedEvent(bookId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PatchMapping("/{bookId}")
    fun renameById(@PathVariable bookId: String, @Valid @RequestBody request: RenameBookRequest): ResponseEntity<Void> {
        logger.info { "Renaming $bookId" }
        bookService.rename(bookId, request.newTitle)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}