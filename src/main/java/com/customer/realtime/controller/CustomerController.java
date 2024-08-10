package com.customer.realtime.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.realtime.model.Customer;
import com.customer.realtime.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/batch-create")
    public ResponseEntity<Map<String, Object>> createCustomer(@RequestBody List<Customer> isi) {
        log.info("Mengakses Create Batch Customer");
        Map<String, Object> data = customerService.createCustomer(isi);

        return new ResponseEntity<>(data, HttpStatus.valueOf((Integer) data.get("statusCode")));
    }

    @GetMapping("/name-read")
    public ResponseEntity<Map<String, Object>> readCustomers(@RequestParam String namaCustomer) {
        log.info("Mengakses Read Customer by Name");
        Map<String, Object> data = customerService.readCustomerByName(namaCustomer);

        return new ResponseEntity<>(data, HttpStatus.valueOf((Integer) data.get("statusCode")));
    }

    @GetMapping("/batch-read")
    public ResponseEntity<Map<String, Object>> readCustomerByName() {
        log.info("Mengakses Read Batch Customer");
        Map<String, Object> data = customerService.readCustomer();

        return new ResponseEntity<>(data, HttpStatus.valueOf((Integer) data.get("statusCode")));
    }

    @PutMapping("/batch-update")
    public ResponseEntity<Map<String, Object>> UpdateCustomerCustomer(@RequestBody List<Customer> isi) {
        log.info("Mengakses Update Batch Customer");
        Map<String, Object> data = customerService.UpdateCustomer(isi);

        return new ResponseEntity<>(data, HttpStatus.valueOf((Integer) data.get("statusCode")));
    }

    @DeleteMapping("/batch-delete")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@RequestBody List<Long> isi) {
        log.info("Mengakses Delete Batch Customer");
        Map<String, Object> data = customerService.deleteCustomer(isi);

        return new ResponseEntity<>(data, HttpStatus.valueOf((Integer) data.get("statusCode")));
    }
}
