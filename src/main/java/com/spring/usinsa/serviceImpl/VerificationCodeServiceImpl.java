package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.repository.VerificationCodeRepository;
import com.spring.usinsa.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode findByCode(String code) {
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
                .orElseThrow(() -> new ApiException(ApiErrorCode.INVALID_PARAMS));

        if (verificationCode.getUser() == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        return verificationCode;
    }

    @Override
    @Transactional
    public VerificationCode createCode(User user) {
        String secretCode = UUID.randomUUID().toString();

        VerificationCode verificationCode = VerificationCode.builder()
                .code(secretCode)
                .user(user)
                .build();

        if(existsByCode(secretCode)) {
            throw new ApiException(ApiErrorCode.SERVER_TRANSACTION_ERROR);
        }

        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode verifyCode(String code) {

        VerificationCode verificationCode = findByCode(code);

        // 만료시간 검증로직 ( 5분 )
        if(verificationCode.getCreatedAt() + (1000l * 60 * 5) < System.currentTimeMillis()) {
            throw new ApiException(ApiErrorCode.VERIFICATION_CODE_EXPIRED);
        }

        return verificationCode;
    }

    @Override
    public boolean existsByCode(String code) {
        return verificationCodeRepository.existsByCode(code);
    }

    @Override
    public void delete(VerificationCode verificationCode) {
        try {
            verificationCodeRepository.delete(verificationCode);
        } catch (Exception e) {
            log.error("Verification Code Delete Fail ! --", e);
            throw new ApiException(ApiErrorCode.SERVER_TRANSACTION_ERROR);
        }
    }
}
