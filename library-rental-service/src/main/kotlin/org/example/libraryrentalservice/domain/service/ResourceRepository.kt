package org.example.libraryrentalservice.domain.service

interface ResourceRepository {
    fun setQuantity(bookId: String, quantity: Int)
}