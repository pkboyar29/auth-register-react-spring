package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@Getter
@Setter
public class User {

    private enum AGE_LIMIT {
        under18,
        over18
    }
    private enum GENDER {
        male,
        female
    }
    private enum THEME {
        light,
        dark
    }

    @Id
    @Column(name = "id", unique = true, updatable = false)
    private String id;
    @Column(name = "username", unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AGE_LIMIT ageLimit;
    @Enumerated(EnumType.STRING)
    private GENDER gender;
    private Boolean acceptRules;
    @Enumerated(EnumType.STRING)
    private THEME theme;
    private LocalDateTime created;
}
