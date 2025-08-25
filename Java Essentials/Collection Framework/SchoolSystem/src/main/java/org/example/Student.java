package org.example;

import java.util.Map;

public class Student {
    private String name;
    private Map<String, Integer> coursesAndGrades;

    private Student(String name, Map<String, Integer> coursesAndGrades) {
        this.name = name;
        this.coursesAndGrades = coursesAndGrades;
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
