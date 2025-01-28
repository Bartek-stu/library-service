package org.example.librarycatalogservice

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.librarycatalogservice.domain.model.Book
import org.example.librarycatalogservice.domain.service.BookRepository
import org.example.librarycatalogservice.domain.service.BookService
import org.example.librarycatalogservice.domain.service.ElementDoesNotExistException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class BookServiceTest {

    private val bookRepository: BookRepository = mockk()
    private val bookService = BookService(bookRepository)

    @Test
    fun `should return all books`() {
        // Arrange
        val books = listOf(Book("1", "Title1", "Author1"), Book("2", "Title2", "Author2"))
        every { bookRepository.findAll() } returns books

        // Act
        val result = bookService.findAll()

        // Assert
        assertEquals(books, result)
        verify { bookRepository.findAll() }
    }

    @Test
    fun `should throw exception when book not found by id`() {
        // Arrange
        every { bookRepository.findById("invalid-id") } returns Optional.empty()

        // Act & Assert
        assertThrows<ElementDoesNotExistException> {
            bookService.findById("invalid-id")
        }
    }

    @Test
    fun `should rename book by id`() {
        // Arrange
        val bookId = "1"
        val newTitle = "New Title"
        every { bookRepository.rename(bookId, newTitle) } returns Unit

        // Act
        bookService.rename(bookId, newTitle)

        // Assert
        verify { bookRepository.rename(bookId, newTitle) }
    }
}
