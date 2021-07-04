package com.testinglaboratory.restassured.foundations.simple.queryparams;

//TODO exercise query parameters

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create tests for:
 * /get_all_people_sliced
 * /get_all_people_paged
 * /get_all_people_by
 * endpoints
 */

@Slf4j
public class ExerciseQueryParamsPeopleServiceTest {

    @Test
    public void getAllPeopleFromSliced() {
        Response response = given()
                .queryParam("from_number", "1")
                .queryParam("up_to_number", "10")
                .when()
                .get("/get_all_people_sliced")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        List<Map<String, String>> people = response.body()
                .jsonPath()
                .getList(".");
        assertThat(people).hasSizeGreaterThanOrEqualTo(9);
    }

    @Test
    public void getAllPeoplePaged() {
        Response response = given()
                .queryParam("page_size", "1")
                .queryParam("page_number", "2")
                .when()
                .get("/get_all_people_paged")
                .andReturn();

        response.then().statusCode(200);

        String people = response.body()
                .jsonPath()
                .getString("first_name");

        log.info(String.valueOf(people));
        assertThat(people).isEqualTo("[Leonard]");
    }

    @Test
    public void getPeopleByFirstName() {
        Response response = given()
                .queryParam("first_name", "Adam")
                .when()
                .get("/get_people_by")
                .then()
                .log().ifValidationFails().statusCode(200)
                .extract().response();

        List<Map<String, String>> people = response.body().jsonPath().getList(".");

        log.info(String.valueOf(people));
        assertThat(people).hasSize(7);

        people.forEach(stringStringMap ->
                assertThat(
                        stringStringMap.get("first_name"))
                        .isEqualTo("Adam"));
    }
}
