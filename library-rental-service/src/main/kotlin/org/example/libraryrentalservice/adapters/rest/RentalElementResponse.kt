package org.example.libraryrentalservice.adapters.rest

import java.time.Instant

data class RentalElementResponse(val id: String, val userId: String, val bookId: String, val expectedReturnDate: Instant)