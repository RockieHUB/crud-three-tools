package com.customer.realtime.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.realtime.model.Customer;
import com.customer.realtime.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@EnableCaching
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Map<String, Object> createCustomer(List<Customer> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (data.isEmpty()) {
                result.put("message", "Customer gagal ditambahkan");
                result.put("statusCode", HttpStatus.BAD_REQUEST.value());
            } else {
                customerRepository.saveAll(data);
                result.put("message", "Customer berhasil ditambahkan");
                result.put("statusCode", HttpStatus.OK.value());
            }
        } catch (Exception e) {
            result.put("message", "Customer gagal ditambahkan");
            result.put("exception", e.getCause());
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    @Cacheable("customer")
    public Map<String, Object> readCustomerByName(String namaCustomer) {
        doLongRunningTask();
        Map<String, Object> result = new HashMap<>();
        try {
            Optional<Customer> data = customerRepository.findByCustomerName(namaCustomer);
            if (data.isEmpty()) {
                result.put("message", "Customer Gagal dibaca");
                result.put("statusCode", HttpStatus.NOT_FOUND.value());
            } else {
                result.put("data", data.get());
                result.put("message", "Customer berhasil dibaca");
                result.put("statusCode", HttpStatus.OK.value());
            }
        } catch (Exception e) {
            result.put("message", "Customer gagal dibaca");
            result.put("exception", e.getCause());
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    @Cacheable("customers")
    public Map<String, Object> readCustomer() {
        doLongRunningTask();
        Map<String, Object> result = new HashMap<>();
        try {
            List<Customer> data = customerRepository.findAll();
            if (data.isEmpty()) {
                result.put("message", "Customer Gagal dibaca");
                result.put("statusCode", HttpStatus.NOT_FOUND.value());
            } else {
                result.put("data", data);
                result.put("message", "Customer berhasil dibaca");
                result.put("statusCode", HttpStatus.OK.value());
            }
        } catch (Exception e) {
            result.put("message", "Customer gagal dibaca");
            result.put("exception", e.getCause());
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    @Transactional
    public Map<String, Object> UpdateCustomer(List<Customer> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (data.isEmpty()) {
                result.put("message", "Customer gagal diperbarui");
                result.put("statusCode", HttpStatus.BAD_REQUEST.value());
            } else {
                // Cek validasi id & eksistensi id customer di database
                for (Customer customer : data) {
                    if (customer.getCustomerId() == null
                            && customerRepository.findById(customer.getCustomerId()).isEmpty()) {
                        result.put("message",
                                "Customer ID " + customer.getCustomerId() + " data is missing or customer not existed");
                        result.put("statusCode", HttpStatus.BAD_REQUEST.value());
                        return result;
                    }
                }

                customerRepository.saveAll(data);
                result.put("message", "Customer berhasil diperbarui");
                result.put("statusCode", HttpStatus.OK.value());
            }
        } catch (Exception e) {
            result.put("message", "Customer gagal diperbarui");
            result.put("exception", e.getCause());
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    @Transactional
    public Map<String, Object> deleteCustomer(List<Long> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (data.isEmpty()) {
                result.put("message", "Customer gagal dihapus, data yang ingin dihapus tidak ada");
                result.put("statusCode", HttpStatus.BAD_REQUEST.value());
            } else {
                customerRepository.deleteAllById(data);
                result.put("message", "Customer berhasil dihapus");
                result.put("statusCode", HttpStatus.OK.value());
            }
        } catch (Exception e) {
            result.put("message", "Customer gagal dihapus");
            result.put("exception", e.getCause());
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}