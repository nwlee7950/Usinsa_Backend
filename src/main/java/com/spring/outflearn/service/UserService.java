package com.spring.outflearn.service;

import com.spring.outflearn.model.User;

public interface UserService {
    User findByUsername(String username);
    User findFirstByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    User findById(long userId);
}
