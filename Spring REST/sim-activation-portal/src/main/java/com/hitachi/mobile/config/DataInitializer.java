package com.hitachi.mobile.config;

import com.hitachi.mobile.model.*;
import com.hitachi.mobile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SIMDetailsRepository simDetailsRepository;

    @Autowired
    private SIMOffersRepository simOffersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerIdentityRepository customerIdentityRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize SIM Details
        if (simDetailsRepository.count() == 0) {
            SimDetails sim1 = new SimDetails("1274564592", "1274564592000", "active");
            SimDetails sim2 = new SimDetails("1234567892", "1234567892000", "inactive");

            sim1 = simDetailsRepository.save(sim1);
            sim2 = simDetailsRepository.save(sim2);

            // Initialize SIM Offers
            SimOffers offer1 = new SimOffers(100, 100, 120, 10, "Free calls and data", sim1.getSimId());
            SimOffers offer2 = new SimOffers(150, 50, 100, 15, "Free calls", sim2.getSimId());

            simOffersRepository.save(offer1);
            simOffersRepository.save(offer2);

            // Initialize Customer Address
            CustomerAddress address1 = new CustomerAddress("Jayanagar", "Bangalore", "Karnataka", "560041");
            CustomerAddress address2 = new CustomerAddress("sector12", "Noida", "Uttar Pradesh", "567017");

            // Initialize Customers
            Customer customer1 = new Customer();
            customer1.setUniqueIdNumber("9876543212345671");
            customer1.setDateOfBirth(LocalDate.of(1990, 12, 12));
            customer1.setEmailAddress("smith@abc.com");
            customer1.setFirstName("Smith");
            customer1.setLastName("John");
            customer1.setIdType("Aadhar");
            customer1.setCustomerAddress(address1);
            customer1.setSimId(sim1.getSimId());
            customer1.setState("Karnataka");

            Customer customer2 = new Customer();
            customer2.setUniqueIdNumber("9876543212345682");
            customer2.setDateOfBirth(LocalDate.of(1998, 12, 12));
            customer2.setEmailAddress("bob@abc.com");
            customer2.setFirstName("Bob");
            customer2.setLastName("Sam");
            customer2.setIdType("Aadhar");
            customer2.setCustomerAddress(address2);
            customer2.setSimId(sim2.getSimId());
            customer2.setState("Uttar Pradesh");

            customerRepository.save(customer1);
            customerRepository.save(customer2);

            // Initialize Customer Identity
            CustomerIdentity identity1 = new CustomerIdentity("9876543212345671",
                    LocalDate.of(1990, 12, 12), "Smith", "John", "smith@abc.com", "Karnataka");
            CustomerIdentity identity2 = new CustomerIdentity("9876543212345682",
                    LocalDate.of(1998, 12, 12), "Bob", "Sam", "bob@abc.com", "Uttar Pradesh");

            customerIdentityRepository.save(identity1);
            customerIdentityRepository.save(identity2);

            System.out.println("Sample data initialized successfully!");
        }
    }
}
