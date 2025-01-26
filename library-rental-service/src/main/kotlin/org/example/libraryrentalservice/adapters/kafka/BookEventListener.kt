package org.example.libraryrentalservice.adapters.kafka

import mu.KLogging
import org.example.libraryrentalservice.adapters.postgresqldb.JpaResourceRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component



@Component
class BookEventListener(private val jpaResourceRepository: JpaResourceRepository) {

    companion object : KLogging()

    @KafkaListener(topics = ["book-events"], groupId = "rental-service-group")
    fun handleBookAddedEvent(event: BookAddedEvent) {
        logger.info("Received event: $event")
        jpaResourceRepository.save(event.toEntity())
    }
}