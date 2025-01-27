package org.example.librarydiscoveryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class LibraryDiscoveryServiceApplication

fun main(args: Array<String>) {
    runApplication<LibraryDiscoveryServiceApplication>(*args)
}
