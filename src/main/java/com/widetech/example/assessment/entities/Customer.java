package com.widetech.example.assessment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Product entity.
 */
@Data //create getter/setter/hashcode
@Entity //JPA entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long custId;

    @Column(name = "customer_phone_number", unique = true)
    private String custPhoneNumber;

    @Column(name = "customer_email")
    private String custEmail;

    @Column(name = "customer_address")
    private String custAddress;

    //cascading method when this table is delete, the other associated table will be deleted as well
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;
}


