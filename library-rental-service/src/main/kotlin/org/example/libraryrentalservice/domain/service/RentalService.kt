package org.example.libraryrentalservice.domain.service

import org.example.libraryrentalservice.domain.model.Rental

class RentalService(private val rentalRepository: RentalRepository, private val resourceRepository: ResourceRepository) {
    fun add(rental: Rental) = rentalRepository.add(rental)
    fun remove(rentalId: String) = rentalRepository.remove(rentalId)
    fun findAllUserRentals(userId: String) = rentalRepository.findAllByUserId(userId)
    fun setBookQuantity(bookId: String, quantity: Int) = resourceRepository.setQuantity(bookId, quantity)
}