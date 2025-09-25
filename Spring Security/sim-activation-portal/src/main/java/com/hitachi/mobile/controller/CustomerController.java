package com.hitachi.mobile.controller;

import com.hitachi.mobile.dto.*;
import com.hitachi.mobile.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/validate-sim")
    public ResponseEntity<String> validateSimDetails(@Valid @RequestBody SimValidationRequest request) {
        try {
            String result = customerService.validateSimDetails(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validate-basic-details")
    public ResponseEntity<String> validateCustomerBasicDetails(@Valid @RequestBody CustomerBasicDetailsRequest request) {
        try {
            String result = customerService.validateCustomerBasicDetails(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validate-personal-details")
    public ResponseEntity<String> validateCustomerPersonalDetails(@Valid @RequestBody CustomerPersonalDetailsRequest request) {
        try {
            String result = customerService.validateCustomerPersonalDetails(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-address")
    public ResponseEntity<String> updateCustomerAddress(@Valid @RequestBody AddressUpdateRequest request) {
        try {
            String result = customerService.updateCustomerAddress(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validate-id-proof")
    public ResponseEntity<String> validateIdProof(@Valid @RequestBody IdProofValidationRequest request) {
        try {
            String result = customerService.validateIdProof(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
