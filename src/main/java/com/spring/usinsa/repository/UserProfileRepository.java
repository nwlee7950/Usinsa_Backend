package com.spring.usinsa.repository;

import com.spring.usinsa.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(long userId);
    boolean existsByProfileImage(String profileImage);
}
