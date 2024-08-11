package com.customer.realtime.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.customer.realtime.model.Customer;
import com.customer.realtime.repository.postgre.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "customer-create-topic", groupId = "customer-group")
    public void handleCustomerCreation(String message) throws JsonProcessingException {
        List<Customer> customers = objectMapper.readValue(message, new TypeReference<List<Customer>>() {
        });

        customerRepository.saveAll(customers);

        cacheManager.getCache("customers").clear();
    }

    @KafkaListener(topics = "customer-update-topic", groupId = "customer-group")
    public void handleCustomerUpdate(String message) throws JsonProcessingException {
        List<Customer> customers = objectMapper.readValue(message, new TypeReference<List<Customer>>() {
        });

        customerRepository.saveAll(customers);

        cacheManager.getCache("customers").clear();
        customers.forEach(customer -> cacheManager.getCache("customer").evict(customer.getCustomerName()));
    }

    @KafkaListener(topics = "customer-delete-topic", groupId = "customer-group")
    public void handleCustomerDeletion(String message) throws JsonProcessingException {
        List<Long> customerIds = objectMapper.readValue(message, new TypeReference<List<Long>>() {
        });

        customerRepository.deleteAllById(customerIds);

        cacheManager.getCache("customers").clear();
    }
}
