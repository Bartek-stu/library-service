package org.example.libraryrentalservice.domain.service

import org.example.libraryrentalservice.domain.model.Rental

interface RentalRepository {
    fun add(rental: Rental)
    fun findAllByUserId(userId: String): List<Rental>
    fun remove(rentalId: String)
}

class ElementDoesNotExistException : RuntimeException()

class InsufficientResourcesException : RuntimeException()