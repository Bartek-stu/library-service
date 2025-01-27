package org.example.librarycatalogservice.adapters.rest

import org.example.librarycatalogservice.domain.model.Book

internal fun Book.toResponseElement(): BookResponse = BookResponse(id, title, author)

internal fun AddBookRequest.toDomain(bookId: String): Book = Book(bookId, title, author)