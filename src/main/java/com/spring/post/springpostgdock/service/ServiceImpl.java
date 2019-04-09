package com.spring.post.springpostgdock.service;

import com.spring.post.springpostgdock.model.Account;
import com.spring.post.springpostgdock.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(int user_id) {
        return accountRepository.getOne(user_id);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void deleteById(int user_id) {
        accountRepository.deleteById(user_id);
    }

    @Override
    public void create() {
        Account account = new Account(1, "Account");
        accountRepository.save(account);
    }
}
