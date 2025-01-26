package org.example.librarycatalogservice.adapters.rest

import org.example.librarycatalogservice.domain.model.Book

internal fun Book.toResponseElement(): BookElement = BookElement(id, title, author)

internal fun AddBookRequest.toDomain(bookId: String): Book = Book(bookId, title, author)