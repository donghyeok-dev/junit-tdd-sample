package com.example.tdd1;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MockitoDto {
    private String name;
    private Integer value1;
    private Integer value2;

    @Builder
    public MockitoDto(String name, Integer value1, Integer value2) {
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }
}
