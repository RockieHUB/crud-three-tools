package com.customer.realtime.repository.elastic;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.customer.realtime.model.ESCustomer;

public interface CustomerElasticRepository extends ElasticsearchRepository<ESCustomer, Long>{
    Optional<ESCustomer> findByCustomerName(String customerName);
}
