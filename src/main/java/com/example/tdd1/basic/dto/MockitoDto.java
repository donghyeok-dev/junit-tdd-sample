package com.example.tdd1.basic.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "value1", "value2"})
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
