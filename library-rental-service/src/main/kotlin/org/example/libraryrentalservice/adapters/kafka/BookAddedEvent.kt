package org.example.libraryrentalservice.adapters.kafka

import org.example.libraryrentalservice.adapters.postgresqldb.ResourceEntity

data class BookAddedEvent(val bookId: String, val quantity: Int) {
    fun toEntity(): ResourceEntity {
        return ResourceEntity(bookId, quantity)
    }
}
