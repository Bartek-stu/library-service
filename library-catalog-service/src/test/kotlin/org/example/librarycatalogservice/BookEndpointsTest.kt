package org.example.librarycatalogservice

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.librarycatalogservice.adapters.kafka.BookEventProducer
import org.example.librarycatalogservice.adapters.rest.BookEndpoints
import org.example.librarycatalogservice.adapters.rest.AddBookRequest
import org.example.librarycatalogservice.adapters.rest.BookResponse
import org.example.librarycatalogservice.domain.model.Book
import org.example.librarycatalogservice.domain.service.BookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class BookEndpointsTest {

    private val bookService: BookService = mockk()
    private val bookEventProducer: BookEventProducer = mockk(relaxed = true)
    private val bookEndpoints = BookEndpoints(bookService, bookEventProducer)

    @Test
    fun `should return all books`() {
        // Arrange
        val books = listOf(Book("1", "Title1", "Author1"), Book("2", "Title2", "Author2"))
        every { bookService.findAll() } returns books

        // Act
        val result = bookEndpoints.getAll()

        // Assert
        assertEquals(2, result.size)
        verify { bookService.findAll() }
    }

    @Test
    fun `should add new book`() {
        // Arrange
        val request = AddBookRequest("Title1", "Author1")
        every { bookService.add(any()) } returns Unit

        // Act
        val response = bookEndpoints.addNew(request)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        verify { bookService.add(any()) }
        verify { bookEventProducer.sendBookAddedEvent(any()) }
    }
}