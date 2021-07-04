package com.testinglaboratory.restassured.primer;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;

public class PrimerInformationTest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8082";
        RestAssured.basePath = "/challenge/primer";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }

    Faker fake = new Faker(new Locale("PL_pl"));
    String username = fake.name().username();
    String password = fake.internet().password();

    @Test
    @DisplayName("Get /information")
    void getInformation() {
        when().get("/information")
                .then()
                .statusCode(200)
                .log()
                .everything()
                .body("message", equalToCompressingWhiteSpace("Oi! W'at can I do for ya? In this primer " +
                        "for challenges you'll learn how to look for flags. Remember that this is not purely technical " +
                        "task. You'll role play and use your knowledge to find treasures your looking for. If you have " +
                        "any questions - ask. Try and found as many flags as possible.(Five, there are five.) begin " +
                        "with shooting at /tryout."));
    }

    @Test
    @DisplayName("Get information from /flag")
    void getFlagInformation() {
        Response response = when().get("/flag")
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("A flag has a form of ${<flag_name>}");
        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Use your exploratory skills and feel the challenge's theme to obtain flags");
    }

    @Test
    @DisplayName("Flag with ${flag_hello_there}")
    void flagHelloThere() {
        when().get("/flag/1")
                .then()
                .log().everything()
                .statusCode(200)
                .body("flag", equalTo("${flag_hello_there}"));
    }

    @Test
    @DisplayName("Flag with ${flag_general_kenobi}")
    void flagGeneralKenobi() {
        when().get("/flag/6")
                .then()
                .log().everything()
                .statusCode(200)
                .body("flag", equalTo("${flag_general_kenobi}"));
    }

    @ParameterizedTest
    @DisplayName("Checking flag for id={flagId}")
    @ValueSource(ints = {1, 6})
    void flagIDs(int flagId) {
        Response response = given()
                .pathParam("flagId", flagId)
                .when()
                .get("/flag/{flagId}")
                .then()
                .log().everything()
                .statusCode(200)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .matches("\\$\\{flag_.*}");
    }

    @ParameterizedTest
    @DisplayName("Checking empty flag for id={flagId}")
    @ValueSource(ints = {0, 2, 3, 4, 5, 7, 8, 9})
    void flagNotFound(int flagId) {
        Response response = given()
                .pathParam("flagId", flagId)
                .when()
                .get("/flag/{flagId}")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("Nope");
    }

    @Test
    @DisplayName("Register Fake User with JsonObject")
    void registerFakerUser() {
        JsonObject bodyRegister = new JsonObject();
        bodyRegister.addProperty("username", username);
        bodyRegister.addProperty("password", password);

        given()
                .body(bodyRegister)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .statusCode(201);
    }

    @Test
    @DisplayName("Register Fake User with Map.of in Body")
    void registerFakerUserWithMap() {
        Response response = given()
                .body(
                        Map.of(
                                "username", username,
                                "password", password
                        )
                )
                .post("/register")
                .then().log().everything()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        assertThat(response.jsonPath().getString("message"))
                .startsWith("User").endsWith("registered").contains(username);
    }

    @Test
    @DisplayName("Double register Fake User")
    void doubleRegisterFakerUser() {
        JsonObject bodyRegister = new JsonObject();
        bodyRegister.addProperty("username", fake.name().username()+ fake.chuckNorris());
        bodyRegister.addProperty("password", fake.internet().password());

        given()
                .body(bodyRegister)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .statusCode(201);
        given()
                .body(bodyRegister)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .statusCode(400)
        .body("flag", equalTo("${flag_im_still_here_captain}"));
    }

    @Test
    @DisplayName("Flag with Login string User with JsonObject body")
    void flagLoginNaughtyAintYa() {
        JsonObject bodyLogin = new JsonObject();
        bodyLogin.addProperty("username", "string");
        bodyLogin.addProperty("password", "string");

        given()
                .body(bodyLogin)
                .when()
                .post("/login")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("flag", equalTo("${flag_naughty_aint_ya}"));
    }

    @Test
    @DisplayName("Flag with Login string User with Map.of in Body")
    void flagLoginNaughtyAintYa2() {
        given()
                .body(
                        Map.of(
                                "username", "string",
                                "password", "string"))
                .when()
                .post("/login")
                .then()
                .log().everything()
                .statusCode(401)
                .body("flag", equalTo("${flag_naughty_aint_ya}"));
    }
}