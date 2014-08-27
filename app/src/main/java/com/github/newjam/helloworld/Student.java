package com.github.newjam.helloworld;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by newjam on 8/25/14.
 */


public class Student {

    public final static String STUDENT_TABLE = "student";

    public final static String FIRST_NAME = "first_name";
    public final static String LAST_NAME = "last_name";
    public final static String ID = "id";
    public final static String PRESENT = "present";

    private final String firstName;
    private final String lastName;
    private final String id;
    private boolean present;

    public Student(String firstName, String lastName) {
        this(UUID.randomUUID().toString(), firstName, lastName, false);
    }

    public Student(String id, String firstName, String lastName, boolean present) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.present = present;
        this.id = id;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public static List<Student> sampleData() {
        return Arrays.asList(
                new Student("James", "Newman")
                , new Student("Jasmit", "Singh")
                , new Student("Mike", "Epperson")
                , new Student("Ryan", "Justice")
                , new Student("Anton", "Nikolenko")
                , new Student("Steve", "Hipolito")
        );
    }

}