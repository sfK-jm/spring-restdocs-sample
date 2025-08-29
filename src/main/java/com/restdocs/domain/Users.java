package com.restdocs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public static Users of(String name, String email, String password) {
        Users user = new Users();
        user.name = name;
        user.email = email;
        user.password = password;
        user.createdAt = LocalDateTime.now();
        return user;
    }
}
