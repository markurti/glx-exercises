package com.hitachi.mobile.service;

import com.hitachi.mobile.dto.*;
import com.hitachi.mobile.exception.*;
import com.hitachi.mobile.model.*;
import com.hitachi.mobile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public String validateSimDetails(SimValidationRequest request) {
        try {
            Optional<SimDetails> simDetails = simDetailsRepository.findBySimNumberAndServiceNumber(
                    request.getSimNumber(), request.getServiceNumber());

            if (simDetails.isEmpty()) {
                throw new InvalidDetailsException("Invalid SIM number or service number provided");
            }

            if ("active".equals(simDetails.get().getSimStatus())) {
                return "SIM is already active";
            }

            Optional<SimOffers> offer = simOffersRepository.findBySimId(simDetails.get().getSimId());
            if (offer.isPresent()) {
                SimOffers offerDetails = offer.get();
                return String.format("%d calls + %d GB for Rs.%d, Validity: %d days",
                        offerDetails.getCallQty(),
                        offerDetails.getDataQty(),
                        offerDetails.getCost(),
                        offerDetails.getDuration());
            }

            return "SIM validation successful - No offers available";
        } catch (Exception e) {
            throw new InvalidDetailsException("Error validating SIM details: " + e.getMessage());
        }
    }

    public String validateCustomerBasicDetails(CustomerBasicDetailsRequest request) {
        try {
            Optional<Customer> customer = customerRepository.findByEmailAddressAndDateOfBirth(
                    request.getEmail(), request.getDob());

            if (customer.isEmpty()) {
                throw new DetailsDoesNotExistException("No customer found with provided email and date of birth");
            }

            return "Customer basic details validation successful";
        } catch (DetailsDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDetailsException("Error validating customer basic details: " + e.getMessage());
        }
    }

    public String validateCustomerPersonalDetails(CustomerPersonalDetailsRequest request) {
        try {
            Optional<Customer> customer = customerRepository.findByFirstNameAndLastName(
                    request.getFirstName(), request.getLastName());

            if (customer.isEmpty()) {
                throw new CustomerNotFoundException("No customer found with provided first and last name");
            }

            if (!customer.get().getEmailAddress().equals(request.getConfirmEmail())) {
                throw new InvalidEmailException("Email does not match our records");
            }

            return "Personal details validation successful";
        } catch (CustomerNotFoundException | InvalidEmailException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDetailsException("Error validating personal details: " + e.getMessage());
        }
    }

    public String updateCustomerAddress(AddressUpdateRequest request) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(request.getUniqueIdNumber());
            if (customerOpt.isEmpty()) {
                throw new CustomerNotFoundException("Customer not found with provided unique ID");
            }

            Customer customer = customerOpt.get();
            customer.setAddress(request.getAddress());
            customer.setCity(request.getCity());
            customer.setState(request.getState());
            customer.setPinCode(request.getPinCode());

            customerRepository.save(customer);

            return "Customer address updated successfully";
        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDetailsException("Error updating customer address: " + e.getMessage());
        }
    }

    public String validateIdProof(IdProofValidationRequest request) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(request.getAadharNumber());
            if (customerOpt.isEmpty()) {
                throw new CustomerNotFoundException("Customer not found with provided Aadhar number");
            }

            Customer customer = customerOpt.get();

            if (!customer.getFirstName().equals(request.getFirstName()) ||
                    !customer.getLastName().equals(request.getLastName()) ||
                    !customer.getDateOfBirth().equals(request.getDob())) {
                throw new InvalidDetailsException("Provided details do not match our records");
            }

            // Activate SIM if customer has one
            if (customer.getSimId() != null) {
                Optional<SimDetails> simDetailsOpt = simDetailsRepository.findById(customer.getSimId());
                if (simDetailsOpt.isPresent()) {
                    SimDetails simDetails = simDetailsOpt.get();
                    simDetails.setSimStatus("active");
                    simDetailsRepository.save(simDetails);
                    return "ID proof validation successful and SIM activated";
                }
            }

            return "ID proof validation successful";
        } catch (CustomerNotFoundException | InvalidDetailsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDetailsException("Error validating ID proof: " + e.getMessage());
        }
    }
}
