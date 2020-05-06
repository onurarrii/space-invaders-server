package com.group14.termproject.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;
    @Column(unique = true)
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    
}
