package org.example.libraryrentalservice.adapters.postgresqldb

import org.example.libraryrentalservice.domain.model.Rental

internal fun Rental.toEntity(): RentalEntity = RentalEntity(id, userId, bookId, timestamp)