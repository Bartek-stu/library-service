package org.example.libraryrentalservice.adapters.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.example.libraryrentalservice.domain.service.RentalService
import org.example.libraryrentalservice.domain.service.ResourceRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class GetUserRentalsRequest(@field:NotBlank(message = "User id must be present") val userId: String)
data class ReturnBookRequest(@field:NotBlank(message = "Rental id cannot be blank") val rentalId: String)

data class SetBookQuantityRequest(
    @field:NotBlank(message = "Book id must be present") val bookId: String,
    @field:NotBlank(message = "Book quantity must be present") val bookQuantity: Int,
)

@RestController
@RequestMapping("/api/v1/rentals")
class RentalEndpoints(private val rentalService: RentalService, private val resourceRepository: ResourceRepository) {

    @GetMapping
    fun getUserRentals(@Valid @RequestBody request: GetUserRentalsRequest): List<RentalElementResponse> {
        return rentalService.findAllUserRentals(request.userId).map { it.toReponseElement() }
    }

    @PostMapping("{bookId}")
    fun setQuantity(@RequestParam(required=true) bookId: String, @Valid @RequestBody request: SetBookQuantityRequest): ResponseEntity<Void> {
        resourceRepository.setQuantity(request.bookId, request.bookQuantity)
        return ResponseEntity(HttpStatus.NO_CONTENT)
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