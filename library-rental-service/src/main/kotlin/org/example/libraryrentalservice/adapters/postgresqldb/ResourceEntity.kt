package org.example.libraryrentalservice.adapters.postgresqldb

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "resources")
data class ResourceEntity(
    @Id
    val bookId: String,
    var quantity: Int
)
