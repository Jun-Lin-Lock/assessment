package com.widetech.example.assessment.repositories;

import com.widetech.example.assessment.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findById(long id);

    public Customer findByCustPhoneNumber(String custPhoneNumber);
}
