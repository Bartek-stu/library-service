package org.example.libraryrentalservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class LibraryRentalServiceApplication

fun main(args: Array<String>) {
    runApplication<LibraryRentalServiceApplication>(*args)
}
