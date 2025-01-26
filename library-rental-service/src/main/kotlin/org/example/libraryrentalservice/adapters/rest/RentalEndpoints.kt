package org.example.libraryrentalservice.adapters.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.example.libraryrentalservice.domain.service.RentalService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class GetUserRentalsRequest(@field:NotBlank(message = "User id must be present") val userId: String)
data class ReturnBookRequest(@field:NotBlank(message = "Rental id cannot be blank") val rentalId: String)

@RestController
@RequestMapping("/api/v1/rentals")
class RentalEndpoints(private val rentalService: RentalService) {

    @GetMapping
    fun getUserRentals(@Valid @RequestBody request: GetUserRentalsRequest): List<RentalElementResponse> {
        return rentalService.findAllUserRentals(request.userId).map { it.toReponseElement() }
    }

    @PostMapping
    fun rentABook(@Valid @RequestBody request: RentBookRequest) {
        rentalService.add(request.toDomain())
    }

    @DeleteMapping
    fun deleteUserRental(@Valid @RequestBody request: ReturnBookRequest) {
        rentalService.remove(request.rentalId)
    }
}