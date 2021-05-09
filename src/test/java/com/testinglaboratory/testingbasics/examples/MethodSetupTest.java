package com.testinglaboratory.testingbasics.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodSetupTest {
    Pie pie;

    @BeforeEach
    public void bakeMaAPie(){
        pie = new Pie("Vanilla", "Love");
    }

    @Test
    public void shouldBeTraditional(){
        assertEquals("Vanilla", pie.getFlavour());
        assertEquals("Love", pie.getFilling());
        assertEquals(Optional.empty(), pie.getTopping());
    }

    @ParameterizedTest
    @ValueSource(strings = {"sprinkles", "powder", "chocolate"})
    public void shouldBeToppedWithSomething(String topping){
        assertEquals("Vanilla", pie.getFlavour());
        assertEquals("Love", pie.getFilling());
        pie.setTopping(Optional.ofNullable(topping));
        assertEquals(pie.getTopping().orElse("nothing"), topping);
    }

    @Test
    public void shouldBeFlavourful(){
        assertEquals("Vanilla", pie.getFlavour());
        assertEquals("Love", pie.getFilling());
        pie.setFlavour("Blueberry");
        assertEquals("Strawberry", pie.getFlavour(),"https://www.youtube.com/watch?v=2EWWL3niBWY");
    }

}
