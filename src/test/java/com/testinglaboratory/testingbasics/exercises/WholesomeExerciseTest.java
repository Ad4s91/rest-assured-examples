package com.testinglaboratory.testingbasics.exercises;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

/*TODO EXERCISE
 * - Create class Toy having fields: make, name, colour, material
 *  - having methods returning greeting (including name, colour and material)
 *  - create getters and setters
 *  - write tests that put a text file with toy data to a text file in a folder
 *  - use FileManager to operate on files
 *  - remember to prepare data
 *  - remember to clean up after tests
 */
public class WholesomeExerciseTest {

    private static Toy toy;
    @BeforeAll
    public static void createFolder() {
        FileManager.createDirectory("toyFolder");
    }

    @BeforeEach
    public void createEachToy(){
        FileManager.createFile("toyFolder/toyFile.txt");

        toy = new Toy(Faker.instance().company().bs(),
                Faker.instance().funnyName().name(),
                Faker.instance().color().name(),
                Faker.instance().commerce().material());

    }
    @AfterEach
    public void deleteFile(){
        FileManager.deleteFile("toyFolder/toyFile.txt");
    }
    @AfterAll
    public static void deleteFolder(){
        FileManager.deleteFile("toyFolder");
    }

    @Test
    public void textFileWithToyData1() {
        System.out.println(toy.toString());

        FileManager.writeToFileFile("toyFolder/toyFile.txt", toy.toString());

        String textFromFile = FileManager.readFile("toyFolder/toyFile.txt");

        Assertions.assertEquals(textFromFile, toy.toString());
        Assertions.assertEquals(textFromFile, toy.toString()+ " aaa");
    }

    @Test
    public void textFileWithToyData2() {
        System.out.println(toy.toString());
       FileManager.writeToFileFile("toyFolder/toyFile.txt", toy.toString());

        String textFromFile = FileManager.readFile("toyFolder/toyFile.txt");
        Assertions.assertEquals(textFromFile, toy.toString());
    }

    @Test
    public void textFileWithToyData3() {
        System.out.println(toy.toString());
        FileManager.writeToFileFile("toyFolder/toyFile.txt", toy.toString());

        String textFromFile = FileManager.readFile("toyFolder/toyFile.txt");
        Assertions.assertEquals(textFromFile, toy.toString());
    }

    @Test
    public void textFileWithToyData4() {
        System.out.println(toy.toString());
        FileManager.writeToFileFile("toyFolder/toyFile.txt", toy.toString());

        String textFromFile = FileManager.readFile("toyFolder/toyFile.txt");
        Assertions.assertEquals(textFromFile, toy.toString());
    }
}