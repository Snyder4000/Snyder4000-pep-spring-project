package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccount(Account acc) {
        return accountRepository.findByUsername(acc.getUsername());
    }

    public Account getAccountById(int idNum) {
        return accountRepository.findByAccount_id(idNum);
    }
}
