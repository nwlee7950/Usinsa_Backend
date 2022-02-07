package com.spring.usinsa.repository;

import com.spring.usinsa.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByCode(String code);
    Boolean existsByCode(String code);
}
