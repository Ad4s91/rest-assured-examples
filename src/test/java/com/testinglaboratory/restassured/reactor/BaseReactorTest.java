package com.testinglaboratory.restassured.reactor;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseReactorTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8083";
        RestAssured.basePath = "/challenge/reactor";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    key=getKeyFromDesk();
        //resetProgress();
    }

    protected static Faker fake = new Faker();  //new Locale("PL_pl")
    protected static String name = fake.name().username();
    protected static String key;

//    void checkKey() {
//        System.out.println(key);
//        assertThat(key)
//                .matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
//    }

    public static void registerAdamUser() {
        System.out.println("Register Adam User");
        given()
                .body(
                        Map.of(
                                "name", "Adam1"
                        )
                )
                .post("/desk")
                .then().log().everything()
                .statusCode(HttpStatus.SC_CREATED);
    }

    public void resetProgress() {
        System.out.println("Reset Progress");
        when()
                .get(key + "/reset_progress")
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK);
    }

    public Response controlRodsDelete() {
        Response response = when()
                .delete(key + "/control_room/control_rods/11")
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .extract()
                .response();
        return response;
    }

    public Response controlRodsDeleteID(int number) {
        Response response = when()
                .delete(key + "/control_room/control_rods/" + number)
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .extract()
                .response();
        return response;
    }

    public Response fuelRodsAdd() {
        Response response = when()
                .put(key + "/control_room/fuel_rods/1")
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        return response;
    }

    public Response pressAz5() {
        Response response =
                given()
                        .body(Map.of("pressed", true))
                        .when()
                        .put(key + "/control_room/az_5")
                        .then().log().ifValidationFails()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .response();
        return response;
    }

    protected static String getKeyFromDesk() {
        JsonObject user = new JsonObject();
        user.addProperty("name", name);
        Response response = given()
                .when()
                .body(user)
                .post("/desk")
                .then().log().everything()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();
        return response.jsonPath().getString("key");
    }
}