package com.restdocs.request;

import lombok.Getter;

@Getter
public class UserRequest {
    private String name;
    private String email;
    private String password;

    public static UserRequest of(String name, String email, String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.name = name;
        userRequest.email = email;
        userRequest.password = password;
        return userRequest;
    }
}
