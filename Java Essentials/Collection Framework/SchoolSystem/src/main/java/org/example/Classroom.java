package org.example;

import java.util.HashSet;
import java.util.Set;

public class Classroom {
    private Set<Student> classroom;

    public Classroom() {
        classroom = new HashSet<Student>();
    }

    public void addStudent(Student student) {
        classroom.add(student);
        System.out.println("Student added to class:" + student.getName());
    }

    public void removeStudent(Student student) {
        if (classroom.isEmpty()) {
            System.out.println("Classroom is empty.");
            return;
        }

        if (classroom.contains(student)) {
            classroom.remove(student);
        }
        else {
            System.out.println("No student with the name " + student + " exists int this classroom.");
        }
    }

    public double calculateAverageClassGrade() throws ClassroomIsEmptyException {
        if (classroom.isEmpty()) {
            throw new ClassroomIsEmptyException("Classroom has no students in it.");
        }

        double average = 0;
        for (Student student : classroom) {
            average += student.calculateAverageGrade();
        }

        return average / classroom.size();
    }
}
