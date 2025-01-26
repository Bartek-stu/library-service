package org.example.librarycatalogservice.adapters.rest

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddBookRequest(
    @field:NotBlank(message = "Title is required and cannot be blank")
    val title: String,

    @field:NotBlank(message = "Author is required and cannot be blank")
    val author: String,

    @field:NotNull(message = "Quantity must be specified")
    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity: Int
)