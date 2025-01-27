package org.example.librarycatalogservice.adapters.postgresqldb

import jakarta.persistence.*
import org.example.librarycatalogservice.domain.model.Book

@Entity
@Table(
    name = "books",
    uniqueConstraints = [UniqueConstraint(columnNames = ["title", "author"])]
    )
data class BookEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    val author: String,
) {
    fun toDomain(): Book {
        return Book(id, title, author)
    }
}