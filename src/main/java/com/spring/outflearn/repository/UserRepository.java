package com.spring.outflearn.repository;

import com.spring.outflearn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Optional<User> findFirstByUsername(String email);
    Optional<User> findFirstByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    int countById(long id);
}
