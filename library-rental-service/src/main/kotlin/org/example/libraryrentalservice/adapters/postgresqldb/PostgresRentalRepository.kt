package org.example.libraryrentalservice.adapters.postgresqldb

import jakarta.transaction.Transactional
import org.example.libraryrentalservice.domain.model.Rental
import org.example.libraryrentalservice.domain.service.ElementDoesNotExistException
import org.example.libraryrentalservice.domain.service.InsufficientResourcesException
import org.example.libraryrentalservice.domain.service.RentalRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Component

@Component
class PostgresRentalRepository(private val jpaRentalRepository: JpaRentalRepository, private val jpaResourceRepository: JpaResourceRepository): RentalRepository {

    override fun findAllByUserId(userId: String): List<Rental> {
        return jpaRentalRepository.findAllByUserId(userId).map { it.toDomain() }
    }

    override fun remove(rentalId: String) {
        jpaRentalRepository.deleteById(rentalId)
    }

    @Modifying
    @Transactional
    override fun add(rental: Rental) {
        val resource = jpaResourceRepository.findById(rental.bookId).orElseThrow{ ElementDoesNotExistException() }
        if (resource.quantity == 0) {
            throw InsufficientResourcesException()
        }
        jpaRentalRepository.save(rental.toEntity())
    }
}