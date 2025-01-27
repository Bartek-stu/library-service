package org.example.libraryapigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class LibraryApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<LibraryApiGatewayApplication>(*args)
}
