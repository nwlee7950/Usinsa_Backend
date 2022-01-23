package com.spring.Outflearn.service;

import com.spring.Outflearn.model.User;

public interface UserService {
    User findByUsername(String username);
    User findFirstByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    User findById(long userId);
}
