package com.example.mockwebserver;



import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MockWebServerTest {

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void initialize() {
        final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        final WebClient webClient = WebClient.create(baseUrl);
        userService = new UserService(webClient);
    }
}
