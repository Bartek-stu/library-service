package org.example.libraryrentalservice.adapters.rest

import org.example.libraryrentalservice.domain.service.ElementDoesNotExistException
import org.example.libraryrentalservice.domain.service.InsufficientResourcesException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandler {

    @ExceptionHandler(ElementDoesNotExistException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleElementDoesNotExistException(): ResponseEntity<String> {
        return ResponseEntity("Requested element does not exist.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientResourcesException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleInsufficientResourcesException(): ResponseEntity<String> {
        return ResponseEntity("Insufficient resources of requested entity.", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex.bindingResult.allErrors.associate { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage ?: "Invalid value"
            fieldName to errorMessage
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}