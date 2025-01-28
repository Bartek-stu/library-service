import com.github.tomakehurst.wiremock.client.WireMock.* // Import WireMock
import com.github.tomakehurst.wiremock.junit5.WireMockTest // Użycie WireMock JUnit5 Extension
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(classes = [org.example.librarycatalogservice.LibraryCatalogServiceApplication::class]) // Jawne wskazanie klasy aplikacji
@AutoConfigureMockMvc // Automatyczna konfiguracja MockMvc
@WireMockTest(httpPort = 8082) // WireMock nasłuchuje na porcie 8081
class BookEndpointsIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should send event to external service when book is added`() {
        // Arrange: Konfiguracja stubu WireMock
        stubFor(
            post(urlEqualTo("/events"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"status\": \"success\" }")
                )
        )

        val request = """
            {
                "title": "New Book 0",
                "author": "Author Name "
            }
        """.trimIndent()

        // Act: Wysłanie żądania do kontrolera
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/books") // Twój endpoint
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated) // Sprawdzanie statusu odpowiedzi
        Thread.sleep(1000)
        // Assert: Weryfikacja, czy WireMock otrzymał żądanie
        verify(
            postRequestedFor(urlEqualTo("/events"))
                .withHeader("Content-Type", equalTo("application/json"))

        )
    }
}
