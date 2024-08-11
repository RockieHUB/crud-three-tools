package com.customer.realtime.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "customer")
public class ESCustomer {
    @Id
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNumber;
    private Boolean customerGender;
}
