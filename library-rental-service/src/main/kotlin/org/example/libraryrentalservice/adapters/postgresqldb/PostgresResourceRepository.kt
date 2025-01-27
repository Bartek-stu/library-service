package org.example.libraryrentalservice.adapters.postgresqldb

import jakarta.transaction.Transactional
import org.example.libraryrentalservice.domain.service.ElementDoesNotExistException
import org.example.libraryrentalservice.domain.service.ResourceRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
class PostgresResourceRepository(private val jpaResourceRepository: JpaResourceRepository) : ResourceRepository {

    @Modifying
    @Transactional
    override fun setQuantity(bookId: String, quantity: Int) {
        val resource = jpaResourceRepository.findById(bookId).orElseThrow{ ElementDoesNotExistException() }
        resource.quantity = quantity
        jpaResourceRepository.save(resource)
    }
}