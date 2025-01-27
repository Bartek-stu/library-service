package org.example.librarycatalogservice.adapters.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

data class BookAddedEvent(val bookId: String)

@Component
class BookEventProducer(private val kafkaTemplate: KafkaTemplate<String, BookAddedEvent>) {
    fun sendBookAddedEvent(event: BookAddedEvent) {
        kafkaTemplate.send("book-events", event.bookId, event)
    }
}

