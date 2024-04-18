package com.widetech.example.assessment.services;

import com.widetech.example.assessment.entities.Account;
import com.widetech.example.assessment.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Account service implementation.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        Optional<Account> accSearchResult = accountRepository.findById(id);
        return accSearchResult.orElseGet(Account::new);
    }

    @Override
    public Account createAccount(Account Account) {
        return accountRepository.save(Account);
    }

    @Override
    public Account updateAccount(Account product) {
        return accountRepository.save(product);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
