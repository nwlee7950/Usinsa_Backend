package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ReviewDto;
import com.spring.usinsa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewDto.Response> getReviewList(Long productId, Pageable pageable);
    ReviewDto.Response save(ReviewDto.Request reviewDto);
    void deleteById(Long id, User user);
    Float getPointByProductId(Long productId);
}
