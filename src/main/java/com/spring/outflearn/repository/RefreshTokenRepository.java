package com.spring.outflearn.repository;

import com.spring.outflearn.model.RefreshToken;
import com.spring.outflearn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByUser_IdAndRefreshToken(long userId, String refreshToken);
}
