package com.testinglaboratory.testingbasics.exercises;

import com.github.javafaker.Faker;
import com.github.javafaker.Friends;
import com.github.javafaker.Name;

public class Toy {


    private String make;
    private String name;
    private String colour;
    private String material;

    public Toy(String make, String name, String colour, String material) {
        this.make = make;
        this.name = name;
        this.colour = colour;
        this.material = material;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Welcome {" +
                "name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                ", material='" + material + '\'' +
                '}';
    }

}