package com.hitachi.mobile.service;

import com.hitachi.mobile.dto.*;
import com.hitachi.mobile.exception.*;
import com.hitachi.mobile.model.*;
import com.hitachi.mobile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl {

    @Autowired
    private SIMDetailsRepository simDetailsRepository;

    @Autowired
    private SIMOffersRepository simOffersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerIdentityRepository customerIdentityRepository;

    // SIM Number Validation
    public String validateSimDetails(SimValidationRequest request) {
        // Validate SIM number (13 digits)
        if (request.getSimNumber() == null || !request.getSimNumber().matches("\\d{13}")) {
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }

        // Validate Service number (10 digits)
        if (request.getServiceNumber() == null || !request.getServiceNumber().matches("\\d{10}")) {
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }

        // Find SIM details
        Optional<SimDetails> simDetails = simDetailsRepository.findBySimNumberAndServiceNumber(
                request.getSimNumber(), request.getServiceNumber());

        if (simDetails.isEmpty()) {
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }

        // Check if SIM is already active
        if ("active".equals(simDetails.get().getSimStatus())) {
            return "Subscriber Identity Module (SIM)already active";
        }

        // Get offer details
        Optional<SimOffers> offer = simOffersRepository.findBySimId(simDetails.get().getSimId());
        if (offer.isPresent()) {
            SimOffers offerDetails = offer.get();
            return String.format("%d calls + %d GB for Rs.%d, Validity: %d days.",
                    offerDetails.getCallQty(),
                    offerDetails.getDataQty(),
                    offerDetails.getCost(),
                    offerDetails.getDuration());
        }

        return "SIM validation successful";
    }

    // Customer Basic Details Validation
    public String validateCustomerBasicDetails(CustomerBasicDetailsRequest request) {
        // Check mandatory fields
        if (request.getEmail() == null || request.getEmail().isEmpty() || request.getDob() == null) {
            return "Email/dob value is required";
        }

        // Validate email format
        String emailPattern = "^[^@]+@[^@]+\\.[a-zA-Z]{2,3}$";
        if (!Pattern.matches(emailPattern, request.getEmail())) {
            return "Invalid email";
        }

        // Check if customer exists
        Optional<Customer> customer = customerRepository.findByEmailAddressAndDateOfBirth(
                request.getEmail(), request.getDob());

        if (customer.isEmpty()) {
            throw new DetailsDoesNotExistException("No request placed for you.");
        }

        return "Validation successful";
    }

    // Customer Personal Details Validation
    public String validateCustomerPersonalDetails(CustomerPersonalDetailsRequest request) {
        // Validate first name and last name
        if (request.getFirstName() == null || request.getFirstName().length() > 15 ||
                !request.getFirstName().matches("[a-zA-Z]+")) {
            return "Firstname/Lastname should be a maximum of 15 characters";
        }

        if (request.getLastName() == null || request.getLastName().length() > 15 ||
                !request.getLastName().matches("[a-zA-Z]+")) {
            return "Firstname/Lastname should be a maximum of 15 characters";
        }

        // Find customer by name
        Optional<Customer> customer = customerRepository.findByFirstNameAndLastName(
                request.getFirstName(), request.getLastName());

        if (customer.isEmpty()) {
            return "No customer found for the provided details";
        }

        // Validate email match
        if (!customer.get().getEmailAddress().equals(request.getConfirmEmail())) {
            throw new InvalidEmailException("Invalid email details!!");
        }

        return "Personal details validation successful";
    }

    // Update Customer Address
    public String updateCustomerAddress(AddressUpdateRequest request) {
        // Validate address length
        if (request.getAddress() != null && request.getAddress().length() > 25) {
            return "Address should be maximum of 25 characters";
        }

        // Validate PIN code
        if (request.getPinCode() == null || !request.getPinCode().matches("\\d{6}")) {
            return "Pin should be 6 digit number";
        }

        // Validate city and state
        String cityStatePattern = "^[a-zA-Z ]+$";
        if (request.getCity() != null && !Pattern.matches(cityStatePattern, request.getCity())) {
            return "City/State should not contain any special characters except space";
        }

        if (request.getState() != null && !Pattern.matches(cityStatePattern, request.getState())) {
            return "City/State should not contain any special characters except space";
        }

        // Find customer
        Optional<Customer> customer = customerRepository.findById(request.getUniqueIdNumber());
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer Not Found message");
        }

        // Update address
        Customer existingCustomer = customer.get();
        CustomerAddress address = existingCustomer.getCustomerAddress();
        if (address == null) {
            address = new CustomerAddress();
            existingCustomer.setCustomerAddress(address);
        }

        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPinCode(request.getPinCode());

        customerRepository.save(existingCustomer);

        return "Address updated successfully";
    }

    // ID Proof Validation
    public String validateIdProof(IdProofValidationRequest request) {
        // Validate Aadhar number (16 digits)
        if (request.getUniqueIdNumber() == null || !request.getUniqueIdNumber().matches("\\d{16}")) {
            return "Id should be 16 digits";
        }

        // Validate other fields
        if (request.getFirstName() == null || request.getLastName() == null || request.getDateOfBirth() == null) {
            return "Invalid details";
        }

        // Find customer identity
        Optional<CustomerIdentity> identity = customerIdentityRepository.findById(request.getUniqueIdNumber());
        if (identity.isEmpty()) {
            throw new CustomerNotFoundException("Customer Not Found message");
        }

        // Validate details match
        CustomerIdentity customerIdentity = identity.get();
        if (!customerIdentity.getFirstName().equals(request.getFirstName()) ||
                !customerIdentity.getLastName().equals(request.getLastName()) ||
                !customerIdentity.getDateOfBirth().equals(request.getDateOfBirth())) {
            return "Invalid details";
        }

        // Find and activate SIM
        Optional<Customer> customer = customerRepository.findById(request.getUniqueIdNumber());
        if (customer.isPresent()) {
            Long simId = customer.get().getSimId();
            Optional<SimDetails> simDetails = simDetailsRepository.findById(simId);
            if (simDetails.isPresent()) {
                SimDetails sim = simDetails.get();
                sim.setSimStatus("active");
                simDetailsRepository.save(sim);
            }
        }

        return "ID proof validation successful and SIM activated";
    }
}

