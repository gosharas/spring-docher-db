package com.spring.post.springpostgdock.service;

import com.spring.post.springpostgdock.model.Account;

import java.util.List;

public interface Service {
    public List<Account> getAll();

    public Account getAccountById(int user_id);

    public void save(Account account);

    public void deleteById(int user_id);

    public void create();

}
