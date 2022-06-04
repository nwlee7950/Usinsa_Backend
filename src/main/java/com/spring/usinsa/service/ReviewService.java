package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ReviewBodyUpdateRequestDto;
import com.spring.usinsa.dto.product.ReviewRequestDto;
import com.spring.usinsa.dto.product.ReviewResponseDto;
import com.spring.usinsa.model.product.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Review save(ReviewRequestDto reviewDto, Long userId);
    Page<ReviewResponseDto> findByProductId(Long productId, Pageable pageable);
    Page<ReviewResponseDto> findByUserId(Long userId, Pageable pageable);
    void setNicknameByUserId(String nickname, Long userId);
    ReviewResponseDto update(ReviewBodyUpdateRequestDto reviewDto);
}
