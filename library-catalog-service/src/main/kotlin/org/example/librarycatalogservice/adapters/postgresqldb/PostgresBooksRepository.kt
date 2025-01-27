package org.example.librarycatalogservice.adapters.postgresqldb

import org.example.librarycatalogservice.domain.model.Book
import org.example.librarycatalogservice.domain.service.BookRepository
import org.example.librarycatalogservice.domain.service.ElementDoesNotExistException
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class PostgresBooksRepository(private val jpaBookRepository: JpaBookRepository): BookRepository {

    override fun add(book: Book) {
        jpaBookRepository.save(book.toEntity())
    }

    override fun findById(bookId: String): Optional<Book> = jpaBookRepository.findById(bookId).map{ it.toDomain() }

    override fun findByTitle(title: String): Optional<Book> = jpaBookRepository.findByTitle(title).map{ it.toDomain() }

    override fun findAll(): List<Book> = jpaBookRepository.findAll().map{ it.toDomain() }

    @Modifying
    @Transactional
    override fun rename(bookId: String, newTitle: String) {
        val book = jpaBookRepository.findById(bookId).orElseThrow{ ElementDoesNotExistException() }
        book.title = newTitle
        jpaBookRepository.save(book)
    }
}