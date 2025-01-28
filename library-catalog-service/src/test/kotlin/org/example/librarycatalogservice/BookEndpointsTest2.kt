package org.example.librarycatalogservice

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.WireMockServer
import org.example.librarycatalogservice.adapters.rest.BookEndpoints
import org.example.librarycatalogservice.adapters.kafka.BookEventProducer
import org.example.librarycatalogservice.domain.service.BookService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookEndpointsTest2 {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var mockMvc: MockMvc
    private val bookService: BookService = mock(BookService::class.java)
    private val bookEventProducer: BookEventProducer = mock(BookEventProducer::class.java)

    private lateinit var restTemplate: TestRestTemplate

    // WireMockServer is manually instantiated here
    private lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun setUp() {
        restTemplate = TestRestTemplate()
        mockMvc = MockMvcBuilders.standaloneSetup(BookEndpoints(bookService, bookEventProducer))
            .build()

        // Initialize WireMock server
        wireMockServer = WireMockServer(WireMockConfiguration.options().port(8082))  // Set port
//        wireMockServer.start()

        // Configure stubbing for WireMock
//        wireMockServer.stubFor(post(urlEqualTo("/kafka"))
//            .withRequestBody(matchingJsonPath("$.title"))
//            .willReturn(aResponse().withStatus(200)))
        wireMockServer.stubFor(
            post(urlEqualTo("/kafka"))
                .withRequestBody(matchingJsonPath("$.title"))  // Dopasowanie dla tytułu
                .withRequestBody(matchingJsonPath("$.author")) // Dopasowanie dla autora
                .withRequestBody(matchingJsonPath("$.timestamp", matching("\\d+"))) // Dopasowanie dla dynamicznego timestampu
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"status\": \"success\" }")
                    .withStatus(200))
        )
        wireMockServer.start()
    }

    @AfterEach
    fun tearDown() {
        // Stop WireMock server after tests
        wireMockServer.stop()
    }

    @Test
    fun `should add new book successfully`() {
        // Given
        val bookRequest = """{
        "title": "Test332",
        "author": "Test Author"
    }"""

        // Create headers with Content-Type set to application/json
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        // Create HttpEntity with request body and headers
        val entity = HttpEntity(bookRequest, headers)

        // When
        val response = restTemplate.exchange(
            "http://localhost:$port/api/v1/books",
            HttpMethod.POST,
            entity,
            Void::class.java
        )
        println("Response Status Code: ${response.statusCode}")
        println("Response Body: ${response.body}")
        // Then
        assert(response.statusCode == HttpStatus.CREATED)


        verify(
            postRequestedFor(urlEqualTo("/kafka"))
                .withRequestBody(matchingJsonPath("$.title", equalTo("Test2")))
                .withRequestBody(matchingJsonPath("$.author", equalTo("Test Author")))
                .withRequestBody(matchingJsonPath("$.timestamp", matching("\\d+")))
        )
//        verify(bookEventProducer).sendBookAddedEvent(
//            argThat { event ->
//                event.bookId.isNotBlank() // Sprawdzamy, że bookId nie jest puste
//            }
//        )
    }

    @Test
    fun `should return 400 if request is invalid`() {
        // Given
        val invalidRequest = """{
            "title": "",
            "author": "Test Author"
        }"""

        // When
        val response: ResponseEntity<Void> = restTemplate.postForEntity(
            "http://localhost:$port/api/v1/books",
            invalidRequest,
            Void::class.java
        )

        // Then
        assert(response.statusCode == HttpStatus.BAD_REQUEST)
    }
}

