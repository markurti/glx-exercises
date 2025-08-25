package org.example;

import java.util.ArrayList;
import java.util.List;

public class School {
    private final List<Classroom> school;

    public School() {
        school = new ArrayList<>();
    }

    public void addClassroom(Classroom classroom) {
        school.add(classroom);
    }

    public double calculateAverageSchoolGrade() throws NoClassroomsException {
        if (school.isEmpty()) {
            throw new NoClassroomsException("The school contains no classrooms and students");
        }

        double average = 0;
        for (Classroom classroom : school) {
            average += classroom.calculateAverageClassGrade();
        }

        return average / school.size();
    }
}
