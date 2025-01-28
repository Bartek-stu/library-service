package org.example.librarycatalogservice

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = ["book-events"])
@ActiveProfiles("test")
class BookEndpointsIntegrationTest2 {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun setUp() {
        // Initialize WireMock server to mock Kafka interactions
        wireMockServer = WireMockServer(WireMockConfiguration.options().port(8082))
        wireMockServer.start()

        // Configure WireMock stub for Kafka producer
        wireMockServer.stubFor(
            post(urlEqualTo("/book-events"))
                .withRequestBody(matchingJsonPath("$.bookId")) // Dopasowanie pola bookId
        )
    }

    @AfterEach
    fun tearDown() {
        wireMockServer.stop()
    }

    @Test
    fun `should add a new book and send an event to Kafka`() {
        // Given: JSON request body for creating a new book
        val bookRequest = """{
            "title": "Integration Test Book22",
            "author": "Integration Test Author"
        }"""

        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val entity = HttpEntity(bookRequest, headers)

        // When: POST request is sent to the REST endpoint
        val response = restTemplate.exchange(
            "http://localhost:$port/api/v1/books",
            HttpMethod.POST,
            entity,
            Void::class.java
        )

        // Then: Verify the response status
        assertEquals(HttpStatus.CREATED, response.statusCode)

        // And: Verify Kafka message was sent (mocked via WireMock)
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/book-events"))
            .withRequestBody(matchingJsonPath("$.bookId"))
        )
    }
}
