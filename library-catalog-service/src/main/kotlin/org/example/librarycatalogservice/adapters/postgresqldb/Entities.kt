package org.example.librarycatalogservice.adapters.postgresqldb

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.librarycatalogservice.domain.model.Book

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    val id: String,
    val title: String,
    val author: String,
) {
    fun toDomain(): Book {
        return Book(id, title, author)
    }
}