package com.example.mockwebserver;

import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
@Service
public class MockService {

    private final WebTestClient webTestClient;
    public MockService(WebTestClient webTestClient, WebTestClient webTestClient1) {

        this.webTestClient = webTestClient1;
    }

    public Mono<MockDto> getUserById(Integer userId) {
        return webTestClient
                .get()
                .uri("/users/{id}", userId)
                .attribute()
                .bodyToMono(MockDto.class);
    }
}
