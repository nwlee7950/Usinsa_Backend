package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ReviewBodyUpdateRequestDto;
import com.spring.usinsa.dto.product.ReviewRequestDto;
import com.spring.usinsa.dto.product.ReviewResponseDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.product.Review;
import com.spring.usinsa.repository.ReviewRepository;
import com.spring.usinsa.service.ReviewService;
import com.spring.usinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Override
    public Review save(ReviewRequestDto reviewDto, Long userId) {
        User user = userService.findById(userId);

        return reviewRepository.save(reviewDto.toReviewEntity(userId, user.getNickname()));
    }

    @Override
    public Page<ReviewResponseDto> findByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId,pageable)
                .map(ReviewResponseDto::toReviewResponseDto);
    }

    @Override
    public Page<ReviewResponseDto> findByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable)
                .map(ReviewResponseDto::toReviewResponseDto);
    }

    @Override
    public void setNicknameByUserId(String nickname, Long userId) {
        reviewRepository.updateNicknameByUserId(nickname, userId);
    }

    @Override
    public ReviewResponseDto update(ReviewBodyUpdateRequestDto reviewDto) {
        Review review = reviewRepository.getById(reviewDto.getReviewId());

        review.setBody(reviewDto.getBody());
        review.setPoint(reviewDto.getPoint());

        return ReviewResponseDto.toReviewResponseDto(review);
    }
}
