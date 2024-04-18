package com.widetech.example.assessment.repositories;

import com.widetech.example.assessment.entities.Account;
import com.widetech.example.assessment.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Override
    public List<Account> findAll();

    public Account findById(long id);
}
