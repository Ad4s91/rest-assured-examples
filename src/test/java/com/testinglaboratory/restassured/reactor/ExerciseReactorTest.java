package com.testinglaboratory.restassured.reactor;


import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;

//TODO EXERCISE create tests for Reactor challenge
public class ExerciseReactorTest extends BaseReactorTest {

    @Test
    void getInformation() {
        when().get("/information").then().log().everything()
                .body("message", equalToCompressingWhiteSpace("You are the Tech Commander of RBMK reactor " +
                        "power plant. Your task is to perform the reactor test. Bring the power level above 1000 " +
                        "but below 1500 and keep the reactor Operational. Use /{key}/control_room/analysis to peek " +
                        "at reactor core. Use /{key}/control_room to see full info about the reactor. Check in at the " +
                        "/desk to get your key to control room. Put in fuel rods or pull out control rods to raise " +
                        "the power. Put in control rods or pull out fuel rods to decrease the power. " +
                        "There are 12 flags to find. Good luck Commander. "));
    }

    @Test
    @DisplayName("Flag - ${flag_sneaky_rat}")
    void flagSneakyRat() {
        Response response = when()
                .get("/2/control_room")
                .then()
                .log().everything()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("message")).endsWith("${flag_sneaky_rat}");
        //assertThat(response.jsonPath().getString("message")).matches("$\\{flag_sneaky_rat}");
    }

    @Test
    void againRegisterAdamUser() {
        Response response = given()
                .body(
                        Map.of(
                                "name", "Adam1"
                        )
                )
                .post("/desk")
                .then().log().everything()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("A spy?! That Power Plant Tech Commander has already checked in!");
    }

    @Test
    @DisplayName("Flag - ${flag_keeper_of_secret}")
    void registerFakeUser() {
        String nameUser = fake.name().username();
        System.out.println(name);
        Response response = given()
                .body(
                        Map.of(
                                "name", nameUser
                        )
                )
                .post("/desk")
                .then().log().everything()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("key")).hasSize(36);
        assertThat(response.cookie("secret_documentation")).contains("flag_keeper_of_secrets");
    }

    @Test
    @DisplayName("Flag - ${flag_you_didnt_see_the_graphite_because_its_not_there}")
    void flagResetProgressWithExistUser() {
        Response response = when()
                .get("1d37ffc1-9fab-5ecb-ba36-cabb9947febe/reset_progress")
                .then()
                .log().everything()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("${flag_you_didnt_see_the_graphite_because_its_not_there}");
    }

    @Test
    @DisplayName("Flag - ${flag_atomna_elektrostancja_erector}")
    void flagResetProgressWithFakeUser() {
        Response response = when()
                .get("dfasgadfsgvdas/reset_progress")
                .then()
                .log().everything()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("${flag_atomna_elektrostancja_erector}");
    }

    @Test
    @DisplayName("Flag - ${flag_control_rod_manipulator - For x30")
    void changeRodControl() {

        for (int i = 0; i < 30; i++) {
            controlRodsDelete();
        }
        assertThat(controlRodsDelete().jsonPath().getString("flag"))
                .contains("${flag_control_rod_manipulator");
    }


    @Test
    @DisplayName("Flag - ${flag_fuel_rod_manipulator - for x 30")
    void changeFuelRods() {
        for (int i = 0; i < 30; i++) {
            fuelRodsAdd();
        }
        assertThat(fuelRodsAdd().jsonPath().getString("flag"))
                .contains("${flag_fuel_rod_manipulator");
    }

//    @Test
//    @DisplayName("Flag - ${flag_fuel_rod_manipulator - Do While")
//    void changeFuelRodsWithDoWhile() {
//        int i = 0;
//        do {
//            fuelRodsAdd();
//            i++;
//            System.out.println(i);
//        }
//        while (!fuelRodsAdd().jsonPath().getString("flag")
//                .contains("${flag_fuel_rod_manipulator"));
//    }

    @Test
    @DisplayName("Flag - ${flag_dead_int_two_weeks_{name}}")
    void removeAllControlRodsAndPressAZ5Test() {

        for (int i = 0; i <20 ; i++) {
            controlRodsDeleteID(i);
        }
        pressAz5();
        assertThat(pressAz5().jsonPath().getString("flag"))
                .isEqualTo("${flag_dead_int_two_weeks_" + name + "}");
    }

    @Test
    @DisplayName("Flag - ${flag_curious_arent_we_Name}")
    void flagCuriousReactorCore() {
        Response response = when()
                .get("1d37ffc1-9fab-5ecb-ba36-cabb9947febe/reactor_core")
                .then()
                .log().everything()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("${flag_curious_arent_we_Adam1}");
    }
}