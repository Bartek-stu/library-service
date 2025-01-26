package org.example.librarycatalogservice.domain.service

import org.example.librarycatalogservice.domain.model.Book


class BookService(private val bookRepository: BookRepository) {
    fun findAll(): List<Book> = bookRepository.findAll()
    fun findById(id: String): Book = bookRepository.findById(id).orElseThrow { ElementDoesNotExistException() }
    fun findByTitle(title: String): Book = bookRepository.findByTitle(title).orElseThrow{ ElementDoesNotExistException() }
    fun add(book: Book) = bookRepository.add(book)
    fun remove(bookId: String) = bookRepository.remove(bookId)
}