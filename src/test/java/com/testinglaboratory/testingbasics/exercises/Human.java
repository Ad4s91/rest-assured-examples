package com.testinglaboratory.testingbasics.exercises;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class Human {

    private String firstName = "Adam";
    private String lastName = "Testowy";

    public Human getAdam() {
        return builder()
                .firstName("Adam")
                .lastName("Zarzeczny")
                .build();
    }
}
