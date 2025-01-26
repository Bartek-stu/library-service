package org.example.libraryrentalservice.adapters.postgresqldb

import jakarta.persistence.*
import org.example.libraryrentalservice.domain.model.Rental
import java.time.Instant

@Entity
@Table(name = "rentals")
data class RentalEntity(
    @Id
    val id: String,
    val userId: String,
    val bookId: String,

    var timestamp: Instant? = null
) {
    @PrePersist
    fun setTimestampBeforePersist() {
        if (timestamp == null) {
            timestamp = Instant.now()
        }
    }

    fun toDomain(): Rental {
        return Rental(id, userId, bookId, timestamp ?: Instant.now())
    }
}