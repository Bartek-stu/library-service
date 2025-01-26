package org.example.libraryrentalservice.adapters.rest

import org.example.libraryrentalservice.domain.model.Rental
import java.time.Instant
import java.util.UUID

internal fun Rental.toReponseElement(): RentalElementResponse = RentalElementResponse(id, userId, bookId, expectedReturnDate())

internal fun RentBookRequest.toDomain(): Rental = Rental(UUID.randomUUID().toString(), userId, bookId, Instant.now())