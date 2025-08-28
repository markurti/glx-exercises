package org.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao program = new UserDao();

        User user1 = new User(1, "Mark", "mark123@gmail.com");
        User user2 = new User(2, "Gergo", "gergo123@gmail.com");
        User user3 = new User(3, "Lilla", "lilla123@gmail.com");
        User user4 = new User(4, "Abigel", "abigel123@gmail.com");

        try {
            // Add all users to the database
            program.insert(user1);
            program.insert(user2);
            program.insert(user3);
            program.insert(user4);

            // Check if all users were added to the database
            checkDatabaseStatus(program);
            System.out.println(); // empty line for spacing

            // Update every user to Julia
            user1.setName("Julia");
            user2.setName("Julia");
            user3.setName("Julia");
            user4.setName("Julia");

            program.update(user1, user1.getId());
            program.update(user2, user2.getId());
            program.update(user3, user3.getId());
            program.update(user4, user4.getId());

            // Check if users were updated
            checkDatabaseStatus(program);
            System.out.println(); // empty line for spacing

            // Remove all users from the database
            program.delete(user1.getId());
            program.delete(user2.getId());
            program.delete(user3.getId());
            program.delete(user4.getId());

            // Check if all users were deleted
            checkDatabaseStatus(program);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Get and list the number of users in the database
    public static void checkDatabaseStatus(UserDao program) throws SQLException {

        List<User> users = program.getAll();

        System.out.println("The total number of users: " + users.size());
        if (users.isEmpty()) { return; } // exit early if there are no users

        System.out.println("Users in the database:");
        for (User user : users) {
            System.out.println("[" + user.getId() + ", " + user.getName() + ", " + user.getEmail() + "]");
        }
    }
}