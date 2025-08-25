package org.example;

import java.util.HashSet;
import java.util.Set;

public class Classroom {
    private Set<Student> students;

    public Classroom() {
        students = new HashSet<Student>();
    }

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added: " + student);
    }

    public double calculateAverageClassGrade() {
        double average = 0;
        for (Student student : students) {
            average += student.calculateAverageGrade();
        }

        return average / students.size();
    }
}
