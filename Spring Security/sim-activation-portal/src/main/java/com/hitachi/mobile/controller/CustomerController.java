package com.hitachi.mobile.controller;

import com.hitachi.mobile.dto.*;
import com.hitachi.mobile.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/validate-sim")
    public ResponseEntity<String> validateSimDetails(@RequestBody SimValidationRequest request) {
        String result = customerService.validateSimDetails(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-basic-details")
    public ResponseEntity<String> validateCustomerBasicDetails(@RequestBody CustomerBasicDetailsRequest request) {
        String result = customerService.validateCustomerBasicDetails(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-personal-details")
    public ResponseEntity<String> validateCustomerPersonalDetails(@RequestBody CustomerPersonalDetailsRequest request) {
        String result = customerService.validateCustomerPersonalDetails(request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-address")
    public ResponseEntity<String> updateCustomerAddress(@RequestBody AddressUpdateRequest request) {
        String result = customerService.updateCustomerAddress(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-id-proof")
    public ResponseEntity<String> validateIdProof(@RequestBody IdProofValidationRequest request) {
        String result = customerService.validateIdProof(request);
        return ResponseEntity.ok(result);
    }
}
