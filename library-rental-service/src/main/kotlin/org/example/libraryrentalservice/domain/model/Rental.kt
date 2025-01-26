package org.example.libraryrentalservice.domain.model

import java.time.Instant
import java.time.Duration

class Rental(val id: String, val userId: String, val bookId: String, val timestamp: Instant) {
    companion object {
        val MAX_RENTAL_DURATION: Duration = Duration.ofDays(14)
    }

    fun isRentalDurationExceeded(currentTimestamp: Instant): Boolean = Duration.between(currentTimestamp, timestamp) > MAX_RENTAL_DURATION
    fun expectedReturnDate(): Instant = timestamp + MAX_RENTAL_DURATION
}
