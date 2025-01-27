package org.example.librarycatalogservice.domain.service

import org.example.librarycatalogservice.domain.model.Book
import java.util.*

interface BookRepository {
    fun add(book: Book)
    fun findById(bookId: String): Optional<Book>
    fun findByTitle(title: String): Optional<Book>
    fun rename(bookId: String, newTitle: String)
    fun findAll(): List<Book>
}

class ElementDoesNotExistException: RuntimeException()