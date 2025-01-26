package org.example.libraryrentalservice.adapters.rest

import jakarta.validation.constraints.NotBlank

data class RentBookRequest(
    @field:NotBlank(message = "Book ID is required and cannot be blank")
    val bookId: String,

    @field:NotBlank(message = "User ID is required and cannot be blank")
    val userId: String
)