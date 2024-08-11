package com.customer.realtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.realtime.model.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByCustomerName(String customerName);
}
