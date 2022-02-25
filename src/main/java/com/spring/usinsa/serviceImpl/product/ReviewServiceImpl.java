package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ReviewDto;
import com.spring.usinsa.model.product.Review;
import com.spring.usinsa.repository.ReviewRepository;
import com.spring.usinsa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    @Override
    public Review save(ReviewDto.Request reviewDto, long userId) {
        Review review = reviewDto.toReviewEntity();
        review.setUserId(userId);
        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findByProductId(ReviewDto.FindRequest reviewDto) {
//        return reviewRepository.findByProductId(reviewDto.getProductId(),
//                PageRequest.of(reviewDto.getPage(), 20) );
        return null;
    }
}
