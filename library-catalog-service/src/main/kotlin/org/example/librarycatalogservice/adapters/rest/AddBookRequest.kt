package org.example.librarycatalogservice.adapters.rest

import jakarta.validation.constraints.NotBlank

data class AddBookRequest(
    @field:NotBlank(message = "Book title cannot be blank")
    val title: String,
    @field:NotBlank(message = "Author field cannot be blank")
    val author: String
)
