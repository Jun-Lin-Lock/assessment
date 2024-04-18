package com.widetech.example.assessment.services;

import com.widetech.example.assessment.entities.Account;
import com.widetech.example.assessment.entities.Customer;
import com.widetech.example.assessment.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Customer service implementation.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Page<Customer> getAllCustomer(Pageable pageable) { //Pageable for pagination
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> custSearchResult = customerRepository.findById(id);
        return custSearchResult.orElse(null);
    }

    @Override
    public Customer getCustomerByCustPhoneNumber(String phoneNum) {
        Optional<Customer> custSearchResult = Optional.ofNullable(customerRepository.findByCustPhoneNumber(phoneNum));
        return custSearchResult.orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        // Create a copy of the list of accounts to avoid ConcurrentModificationException
        List<Account> accounts = new ArrayList<>(customer.getAccounts());
        if (!accounts.isEmpty()) {
            // Set the customer for each associated account (account will not be null when saving to database)
            for (Account account : accounts) {
                account.setCustomer(customer);
            }
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer custToUpdate, Customer newCustData) {
        // Update customer with new data
        custToUpdate.setCustPhoneNumber(Objects.isNull(newCustData.getCustPhoneNumber()) || newCustData.getCustPhoneNumber().isEmpty() ? custToUpdate.getCustPhoneNumber() : newCustData.getCustPhoneNumber());
        custToUpdate.setCustAddress(Objects.isNull(newCustData.getCustAddress()) || newCustData.getCustAddress().isEmpty() ? custToUpdate.getCustAddress() : newCustData.getCustAddress());
        custToUpdate.setCustEmail(Objects.isNull(newCustData.getCustAddress()) || newCustData.getCustAddress().isEmpty() ? custToUpdate.getCustAddress() : newCustData.getCustAddress());

        //updating account data
        if (Objects.nonNull(newCustData.getAccounts()) && !newCustData.getAccounts().isEmpty()) {
            // create HashMap of new account data
            HashMap<Long, Account> accListToUpdate = new HashMap<>();
            newCustData.getAccounts().forEach(x -> accListToUpdate.put(x.getAccId(), x));

            if (Objects.nonNull(custToUpdate.getAccounts()) && !custToUpdate.getAccounts().isEmpty()) {
                List<Account> updateAcc = new ArrayList<>(custToUpdate.getAccounts().stream().map(x -> {
                    Account currentAcc = accListToUpdate.get(x.getAccId());
                    if (Objects.nonNull(currentAcc)) {
                        x.setAccBalance(Objects.isNull(currentAcc.getAccBalance()) ? x.getAccBalance() : currentAcc.getAccBalance());
                        x.setAccName(Objects.isNull(currentAcc.getAccName()) || currentAcc.getAccName().isEmpty() ? x.getAccName() : currentAcc.getAccName());
                        x.setAccType(Objects.isNull(currentAcc.getAccType()) || currentAcc.getAccType().isEmpty() ? x.getAccType() : currentAcc.getAccType());
                    }
                    accListToUpdate.remove(x.getAccId());
                    return x;
                }).toList());
                for (Long i : accListToUpdate.keySet()) {
                    Account account = accListToUpdate.get(i);
                    account.setCustomer(custToUpdate);
                    updateAcc.add(account);
                }
                custToUpdate.setAccounts(updateAcc);
            }
            return customerRepository.save(custToUpdate);
        }
        return customerRepository.save(custToUpdate);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}
