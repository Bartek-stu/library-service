package org.example.libraryrentalservice.adapters.postgresqldb

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRentalRepository : JpaRepository<RentalEntity, String> {
    fun findAllByUserId(userId: String): List<RentalEntity>
}