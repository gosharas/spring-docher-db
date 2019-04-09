package com.spring.post.springpostgdock.rest;

import com.spring.post.springpostgdock.model.Account;
import com.spring.post.springpostgdock.repository.AccountRepository;
import com.spring.post.springpostgdock.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api/books")
public class ApplicationController {

    @Autowired
    private ServiceImpl service;

    @GetMapping(path = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHello(){
        return "hi";
    }

    @GetMapping
    public List<Account> getAll(){
        return this.service.getAll();
    }


    @GetMapping(path = "/cr")
    public void create(){
        this.service.create();
    }

    @GetMapping(value = "/{id}")
    public Account getAccountById(@PathVariable("id") int id){
        return this.service.getAccountById(id);
    }

    @PostMapping
    public void save(@RequestBody Account account){
        this.service.save(account);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") int id){
        this.service.deleteById(id);
    }



}
