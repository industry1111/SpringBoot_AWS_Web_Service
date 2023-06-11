package com.example.mockwebserver;



import com.example.book.BookApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookApplication.class)
public class MockWebServerTest {

    private static MockWebServer mockWebServer;
    private static ObjectMapper objectMapper;

    private static MockService mockService;

    @BeforeAll
    static void setup() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
            objectMapper = new ObjectMapper();
            final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void initialize() {
        final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        final WebClient webClient = WebClient.create(baseUrl);
        mockService = new MockService(webClient);
    }

    @AfterAll
    static void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMockWebServer() throws Exception {
        //given
        MockDto mockDto = MockDto.builder()
                .id("1")
                .name("test")
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockDto))
                .addHeader("Content-Type", "application/json"));

        //when
        final Mono<MockDto> mockDtoMono = mockService.getMockDtoById(1);

        //then
        StepVerifier.create(mockDtoMono)
                .expectNextMatches(mockDto1 -> mockDto.getName().equals("test"))
                .verifyComplete();
    }
}
