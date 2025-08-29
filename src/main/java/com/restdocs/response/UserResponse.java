package com.restdocs.response;

import com.restdocs.domain.Users;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public static UserResponse of(Users users) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = users.getId();
        userResponse.name = users.getName();
        userResponse.email = users.getEmail();
        userResponse.createdAt = users.getCreatedAt();
        return userResponse;
    }
}
