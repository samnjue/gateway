package com.api.gateway.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.gateway.entity.User;
import com.api.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }
}