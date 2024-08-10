package com.customer.realtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.realtime.model.Customer;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByCustomerName(String customerName);
}
