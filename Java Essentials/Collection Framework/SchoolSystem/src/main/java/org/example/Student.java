package org.example;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String name;
    private final Map<String, Integer> coursesAndGrades;

    public Student(String name) {
        this.name = name;
        this.coursesAndGrades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCourseAndGrade(String course, int grade) {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative.");
        }

        coursesAndGrades.put(course, grade);
    }

    public void updateGrade(String course, int grade) {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative.");
        }

        coursesAndGrades.replace(course, grade);
    }

    public double calculateAverageGrade() throws NotEnrolledInAnyCoursesException {
        if (coursesAndGrades.isEmpty()) {
            throw new NotEnrolledInAnyCoursesException("Student not enrolled in any courses.");
        }

        double average = 0;
        for (var entry : coursesAndGrades.entrySet()) {
            average += entry.getValue();
        }

        return average / coursesAndGrades.size();
    }
}
