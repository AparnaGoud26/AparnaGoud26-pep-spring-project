package com.example.service;
import com.example.entity.Account;
import com.example.exception.SocialMediaException;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@Service
@ComponentScan
public class AccountService {
 
    private final AccountRepository accountRepository;

 @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new SocialMediaException("Username cannot be blank", 400);
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new SocialMediaException("Password must be at least 4 characters", 400);
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new SocialMediaException("Username already exists", 409);
        }
        return accountRepository.save(account);
    }


    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null; 
    }


}
