package com.testinglaboratory.testingbasics.exercises;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*TODO EXERCISE create tests checking:
   - number of letters in your first name
   - equality of length of your first and last name
   - equality of your first and last name
   - your first name having more than 3 letters
 */
public class BasicsExerciseTest {

    String firstName = "Adam";
    String lastName = "Testowy";
    private static Human human;

    @Test
    public void numberOfLettersInFirstName() {
        assertThat(firstName)
                .as("first name has not 4 letters")
                .hasSize(4);
    }

    @Test
    public void lengthOfYourFirstAndLastName() {
        String myName = firstName + " " + lastName;
        assertThat(myName).hasSize(12);
    }
    @Test
    public void lengthOfYourFirstNameFromBuilder() {
        assertThat(human.getAdam()
                .getFirstName()
                .length())
                .isEqualTo(4);
    }

    @Test
    public void firstNameIsEqualLastName() {
        assertThat(firstName)
                .as("first name is not equal last name")
                .isEqualTo(lastName);
    }

    @Test
    public void firstNameIsNotEqualLastName() {
        assertThat(firstName)
                .as("first name is equal last name")
                .isNotEqualTo(lastName);
    }

    @Test
    public void firstNameHaveMoreThan3letters() {
        assertThat(firstName).hasSizeGreaterThan(3);
    }

}