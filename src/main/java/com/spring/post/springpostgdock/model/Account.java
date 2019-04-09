package com.spring.post.springpostgdock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private int user_id;

    @Column(name = "username", nullable = false)
    private String username;

    public Account(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
    }
}
