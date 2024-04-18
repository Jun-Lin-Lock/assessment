package com.widetech.example.assessment.services;

import com.widetech.example.assessment.entities.Customer;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// CustomerService interface
public interface CustomerService {

    Page<Customer> getAllCustomer(Pageable pageable);

    Customer getCustomerById(Long id);

    @Nullable
    Customer getCustomerByCustPhoneNumber(String phoneNum);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer custToUpdate, Customer newCustData);

    void deleteCustomer(Long id);

}
