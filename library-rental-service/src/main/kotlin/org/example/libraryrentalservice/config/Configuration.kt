package org.example.libraryrentalservice.config

import org.example.libraryrentalservice.domain.service.RentalRepository
import org.example.libraryrentalservice.domain.service.RentalService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun rentalService(rentalRepository: RentalRepository): RentalService {
        return RentalService(rentalRepository)
    }
}