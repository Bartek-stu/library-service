package org.example.librarycatalogservice.adapters.postgresqldb

import org.example.librarycatalogservice.domain.model.Book
import org.example.librarycatalogservice.domain.service.BookRepository
import org.example.librarycatalogservice.domain.service.ElementDoesNotExistException
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostgresBooksRepository(private val jpaBookRepository: JpaBookRepository): BookRepository {

    override fun add(book: Book) {
        jpaBookRepository.save(book.toEntity())
    }

    override fun findById(bookId: String): Optional<Book> = jpaBookRepository.findById(bookId).map{ it.toDomain() }

    override fun findByTitle(title: String): Optional<Book> = jpaBookRepository.findByTitle(title).map{ it.toDomain() }

    override fun findAll(): List<Book> = jpaBookRepository.findAll().map{ it.toDomain() }

    override fun remove(bookId: String) {
        val removedCount = jpaBookRepository.removeById(bookId)
        if (removedCount == 0) {
            throw ElementDoesNotExistException()
        }
    }
}