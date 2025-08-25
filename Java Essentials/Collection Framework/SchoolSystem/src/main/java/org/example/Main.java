package org.example;

public class Main {
    public static void main(String[] args) {

        // Add some sample students
        Student student1 = new Student("Peter Parker");
        student1.addCourseAndGrade("Philosophy", 5);
        student1.addCourseAndGrade("Physics", 10);
        student1.addCourseAndGrade("Computer Science", 8);
        student1.addCourseAndGrade("Geography", 4);
        student1.addCourseAndGrade("History", 7);

        Student student2 = new Student("John Smith");
        student2.addCourseAndGrade("Philosophy", 8);
        student2.addCourseAndGrade("Physics", 10);
        student2.addCourseAndGrade("Computer Science", 8);
        student2.addCourseAndGrade("Geography", 10);
        student2.addCourseAndGrade("History", 6);

        Student student3 = new Student("Sophia Chao");
        student3.addCourseAndGrade("Philosophy", 10);
        student3.addCourseAndGrade("Philosophy", 10);
        student3.addCourseAndGrade("Physics", 10);
        student3.addCourseAndGrade("Computer Science", 9);
        student3.addCourseAndGrade("Geography", 10);
        student3.addCourseAndGrade("History", 9);

        System.out.println(student1.getName() + "'s average is: " + student1.calculateAverageGrade());
        System.out.println(student2.getName() + "'s average is: " + student2.calculateAverageGrade());
        System.out.println(student3.getName() + "'s average is: " + student3.calculateAverageGrade());
        System.out.print('\n');

        // Create 1st classroom
        Classroom classroom1 = new Classroom();
        classroom1.addStudent(student1);
        classroom1.addStudent(student2);
        classroom1.addStudent(student3);

        System.out.println("1st classroom's average is: " + classroom1.calculateAverageClassGrade());

        // Alter student grades
        student1.updateGrade("Philosophy", 9);
        student1.updateGrade("Physics", 9);
        student1.updateGrade("Computer Science", 10);
        student2.updateGrade("Philosophy", 10);

        student3.updateGrade("Physics", 5);
        student3.updateGrade("Computer Science", 5);
        student3.updateGrade("Geography", 5);

        // Create 2nd classroom
        Classroom classroom2 = new Classroom();
        classroom2.addStudent(student1);
        classroom2.addStudent(student2);
        classroom2.addStudent(student3);

        System.out.println("2nd classroom's average is: " + classroom2.calculateAverageClassGrade());
        System.out.print('\n');

        // Create 1st school
        School school1 = new School();

        school1.addClassroom(classroom1);
        school1.addClassroom(classroom2);

        System.out.println("The average grade in school1 is: " + school1.calculateAverageSchoolGrade());

        // Create 2nd school
        School school2 = new School();

        // Alter some grades and classrooms
        student1.updateGrade("Philosophy", 5);
        student1.updateGrade("Physics", 5);
        student1.updateGrade("Computer Science", 2);
        student2.updateGrade("Philosophy", 1);
        classroom2.removeStudent(student1);

        school2.addClassroom(classroom1);
        school2.addClassroom(classroom2);

        System.out.println("The average grade in school2 is: " + school2.calculateAverageSchoolGrade());
    }
}