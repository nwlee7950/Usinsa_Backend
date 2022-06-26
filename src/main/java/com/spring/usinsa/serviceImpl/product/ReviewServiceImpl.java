package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ReviewDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.product.Review;
import com.spring.usinsa.repository.ReviewRepository;
import com.spring.usinsa.repository.customRepository.ReviewRepositoryCustom;
import com.spring.usinsa.service.ReviewService;
import com.spring.usinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final UserService userService;

    @Override
    public Page<ReviewDto.Response> getReviewList(Long productId, Pageable pageable) {
        return reviewRepository.findAllByProductId(productId, pageable)
                .map(ReviewDto.Response::toReviewDtoResponse);
    }

    @Override
    @Transactional
    public ReviewDto.Response save(ReviewDto.Request reviewDto) {
        // 궁금한점. null이면 에러를 orElseThrow 할 수 있는데, null이 아닌경우에는 뭘 써야하나

        // 유저가 같은 제품이 이미 등록한 리뷰가 있는 경우
        if(reviewRepositoryCustom.existsByUserIdAndProductId(reviewDto.getUserId(), reviewDto.getProductId())){
            throw new ApiException(ApiErrorCode.REVIEW_DUPLICATED);
        } else{
            Review review = reviewRepository.save(reviewDto.toReviewEntity());
            return ReviewDto.Response.toReviewDtoResponse(review);
        }
    }

    @Override
    public void deleteById(Long id, User user) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.REVIEW_NOT_FOUND));

        if(review.getUserId() != user.getId() && !user.getRoles().stream().anyMatch(role -> role.equals("ROLE_SUPER_ADMIN"))){
            throw new ApiException(ApiErrorCode.USER_ROLE_NOT_PERMISSION_READ);
        }

        reviewRepository.deleteById(id);
    }

    @Override
    public Float getPointByProductId(Long productId) {
        try {
            Float point = reviewRepository.getPointAvgByProductId(productId);

            // 소수점 첫째자리까지 나오게 반올림
            return  ((Number) Math.round(point * 10)).floatValue() / 10;
        }catch (Exception e){
            return 0f;
        }
    }
}
