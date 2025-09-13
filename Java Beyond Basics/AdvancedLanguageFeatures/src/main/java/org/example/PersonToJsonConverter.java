package org.example;

import java.util.List;

public class PersonToJsonConverter {
    public static String SendPersonsToJSON(List<Person> people) {
        if (people == null || people.isEmpty()) {
            return "[]";
        }

        StringBuilder personObjects = new StringBuilder();

        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);

            String personJson = """
                    {
                        "name": "%s",
                        "age": "%d"
                    }""".formatted(person.getName(), person.getAge());

            personObjects.append("    ").append(personJson.replace("\n", "\n    "));

            if (i < people.size() - 1) {
                personObjects.append(",");
            }
            personObjects.append("\n");
        }

        return """
                [
                    %s
                ]""".formatted(personObjects.toString().trim());
    }
}
