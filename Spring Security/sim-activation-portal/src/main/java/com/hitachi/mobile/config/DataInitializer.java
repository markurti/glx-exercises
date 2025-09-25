package com.hitachi.mobile.config;

import com.hitachi.mobile.model.*;
import com.hitachi.mobile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN, Role.USER));
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("Default admin user created: admin/admin123");
        }

        // Initialize test user
        if (!userRepository.existsByUsername("testuser")) {
            User user = new User();
            user.setUsername("testuser");
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRoles(Set.of(Role.USER));
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("Test user created: testuser/password123");
        }

        // Initialize SIM Details
        if (simDetailsRepository.count() == 0) {
            SimDetails sim1 = new SimDetails("1234567890", "1234567890123", "active");
            SimDetails sim2 = new SimDetails("1234567891", "1234567890124", "inactive");

            sim1 = simDetailsRepository.save(sim1);
            sim2 = simDetailsRepository.save(sim2);

            // Initialize SIM Offers
            SimOffers offer1 = new SimOffers(100, 100, 120, 30, "Premium Plan", sim1.getSimId());
            SimOffers offer2 = new SimOffers(150, 50, 100, 15, "Basic Plan", sim2.getSimId());

            simOffersRepository.save(offer1);
            simOffersRepository.save(offer2);

            // Initialize Customers
            Customer customer1 = new Customer();
            customer1.setUniqueIdNumber("1234567890123456");
            customer1.setDateOfBirth(LocalDate.of(1990, 1, 15));
            customer1.setEmailAddress("john.doe@example.com");
            customer1.setFirstName("John");
            customer1.setLastName("Doe");
            customer1.setIdType("Aadhar");
            customer1.setAddress("123 Main Street");
            customer1.setCity("Bangalore");
            customer1.setState("Karnataka");
            customer1.setPinCode("560001");
            customer1.setSimId(sim1.getSimId());

            Customer customer2 = new Customer();
            customer2.setUniqueIdNumber("1234567890123457");
            customer2.setDateOfBirth(LocalDate.of(1985, 5, 22));
            customer2.setEmailAddress("jane.smith@example.com");
            customer2.setFirstName("Jane");
            customer2.setLastName("Smith");
            customer2.setIdType("Aadhar");
            customer2.setAddress("456 Park Avenue");
            customer2.setCity("Mumbai");
            customer2.setState("Maharashtra");
            customer2.setPinCode("400001");
            customer2.setSimId(sim2.getSimId());

            customerRepository.save(customer1);
            customerRepository.save(customer2);

            System.out.println("Sample data initialized successfully!");
        }
    }
}
