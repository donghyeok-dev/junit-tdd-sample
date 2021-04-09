package com.example.tdd1.atmep;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response {
    private Money change;
    private String message;

    @Builder
    public Response(String message, Money change) {
        this.message = message;
        this.change = change;
    }
}
