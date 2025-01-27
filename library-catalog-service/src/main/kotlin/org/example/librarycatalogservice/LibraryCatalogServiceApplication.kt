package org.example.librarycatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class LibraryCatalogServiceApplication

fun main(args: Array<String>) {
    runApplication<LibraryCatalogServiceApplication>(*args)
}
