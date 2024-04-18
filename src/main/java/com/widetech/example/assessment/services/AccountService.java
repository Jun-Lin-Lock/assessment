package com.widetech.example.assessment.services;

import com.widetech.example.assessment.entities.Account;

import java.util.List;

// ProjectService interface
public interface AccountService {

    List<Account> getAllAccount();

    Account getAccountById(Long id);

    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Long id);

}
