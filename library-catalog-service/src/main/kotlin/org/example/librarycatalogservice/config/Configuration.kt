package org.example.librarycatalogservice.config

import org.example.librarycatalogservice.domain.service.BookRepository
import org.example.librarycatalogservice.domain.service.BookService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun bookService(bookRepository: BookRepository): BookService {
        return BookService(bookRepository)
    }
}