package com.example.mockwebserver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MockDto {

    String id;
    String name;


    @Builder
    public MockDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
