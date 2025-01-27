package org.example.libraryrentalservice.config

import org.example.libraryrentalservice.domain.service.RentalRepository
import org.example.libraryrentalservice.domain.service.RentalService
import org.example.libraryrentalservice.domain.service.ResourceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun rentalService(rentalRepository: RentalRepository, resourceRepository: ResourceRepository): RentalService {
        return RentalService(rentalRepository, resourceRepository)
    }
}