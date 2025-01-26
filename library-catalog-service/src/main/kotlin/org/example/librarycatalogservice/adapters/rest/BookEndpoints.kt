package org.example.librarycatalogservice.adapters.rest

import jakarta.validation.Valid
import mu.KLogging
import org.example.librarycatalogservice.adapters.kafka.BookAddedEvent
import org.example.librarycatalogservice.adapters.kafka.BookEventProducer
import org.example.librarycatalogservice.domain.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/books")
class BookEndpoints(private val bookService: BookService, private val bookEventProducer: BookEventProducer) {

    companion object : KLogging()

    @GetMapping
    fun getAll(): List<BookElement> = bookService.findAll().map{ it.toResponseElement() }

    @GetMapping("/{bookId}")
    fun getById(@PathVariable bookId: String): BookElement = bookService.findById(bookId).toResponseElement()

    @GetMapping("/search")
    fun getByTitle(@RequestParam(required=true) title: String): BookElement {
        logger.info { "Searching for $title" }
        return bookService.findByTitle(title).toResponseElement()
    }

    @PostMapping
    fun addNew(@Valid @RequestBody request: AddBookRequest): ResponseEntity<Void> {
        val bookId = UUID.randomUUID().toString()
        val newBook = request.toDomain(bookId)
        bookService.add(newBook)
        bookEventProducer.sendBookAddedEvent(BookAddedEvent(bookId, request.quantity))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{bookId}")
    fun deleteById(@PathVariable bookId: String): ResponseEntity<Void> {
        logger.info { "Deleting $bookId" }
        bookService.remove(bookId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}