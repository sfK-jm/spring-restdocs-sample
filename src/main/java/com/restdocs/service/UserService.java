package com.restdocs.service;

import com.restdocs.domain.Users;
import com.restdocs.repository.UserRepository;
import com.restdocs.request.UserRequest;
import com.restdocs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        Users users = Users.of(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        Users savedUsers = userRepository.save(users);
        return UserResponse.of(savedUsers);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse findUserById(Long id) {
        Users users = userRepository.findById(id).orElseThrow();
        return UserResponse.of(users);
    }


}
