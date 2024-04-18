package com.widetech.example.assessment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter //lombok
@Setter //lombok
@NoArgsConstructor //lombok
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accId;

    @Column(name = "account_type")
    private String accType;

    @Column(name = "account_name")
    private String accName;

    @Column(name = "account_balance")
    @NotBlank(message = "Must input account balance")
    private BigDecimal accBalance;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName="customer_id", nullable = false)
    private Customer customer;

//    public void setCustomer(Customer customer){
//        if (this.customer != null) {
//            this.customer.getAccounts().remove(this); // Remove account from previous customer
//        }
//        // Ensure bidirectional association
//        if (customer != null) {
//            customer.getAccounts().add(this); // Add account to new customer
//        }
//        this.customer = customer;
//    }
}
