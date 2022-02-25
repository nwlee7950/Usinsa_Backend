package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ReviewDto;
import com.spring.usinsa.model.product.Review;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Review save(ReviewDto.Request reviewDto, long userId);
    Page<Review> findByProductId(ReviewDto.FindRequest reviewDto);
}
