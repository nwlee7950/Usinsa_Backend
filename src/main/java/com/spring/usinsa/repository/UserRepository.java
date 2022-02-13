package com.spring.usinsa.repository;

import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndSocial(String username, Social social);
    Optional<User> findFirstByEmailAndSocial(String email, Social social);

    Optional<User> findFirstByUsername(String username);
    Optional<User> findFirstByEmail(String email);
    Optional<User> findFirstByEmailAndName(String email, String name);
    Optional<User> findFirstBySocialAndSocialId(Social social, String socialId);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySocialAndSocialId(Social social, String socialId);
}
