package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDao program = new UserDao();

        try {
            program.insert(new User(1, "Mark", "mark123@gmail.com"));
            program.update(new User(2, "Gergo", "gergo@gmail.com"), 1);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}