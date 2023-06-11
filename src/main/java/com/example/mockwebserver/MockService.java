package com.example.mockwebserver;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class MockService {

    private final WebClient webClient;

    public MockService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<MockDto> getMockDtoById(Integer id) {
        return webClient.get()
                .uri("/user/{id}",id)
                .retrieve()
                .bodyToMono(MockDto.class);
    }
}
