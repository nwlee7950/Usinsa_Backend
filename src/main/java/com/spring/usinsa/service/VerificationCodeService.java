package com.spring.usinsa.service;

import com.spring.usinsa.model.User;
import com.spring.usinsa.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode findByCode(String code);
    VerificationCode createCode(User user);
    VerificationCode verifyCode(String code);
    boolean existsByCode(String code);
    void delete(VerificationCode verificationCode);
}
