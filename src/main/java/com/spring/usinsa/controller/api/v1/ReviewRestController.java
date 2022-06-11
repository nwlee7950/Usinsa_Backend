package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.ReviewDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.ReviewService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewRestController {
    private final ReviewService reviewService;
    private final ApiResponseService apiResponseService;

    @GetMapping("/{productId}")
    public SingleResponse<Page<ReviewDto.Response>> findReviewList(@PathVariable Long productId, Pageable pageable){
        Page<ReviewDto.Response> reviews = reviewService.getReviewList(productId, pageable);

        return apiResponseService.getSingleResult(reviews);
    }

    @PostMapping
    public SingleResponse<ReviewDto.Response> save(@RequestBody ReviewDto.Request reviewDto, @AuthenticationPrincipal User user){
            reviewDto.setNickname(user.getNickname());
            ReviewDto.Response review = reviewService.save(reviewDto);

            return apiResponseService.getSingleResult(review);
    }

    @DeleteMapping("/{id}")
    public SingleResponse<String> deleteById(@PathVariable Long id, @AuthenticationPrincipal User user){
        reviewService.deleteById(id, user);

        return apiResponseService.getSingleResult("success");
    }

    @GetMapping("/point/{productId}")
    public SingleResponse<Float> getPointByProductId(@PathVariable Long productId){
        Float pointAvg = reviewService.getPointByProductId(productId);

        return apiResponseService.getSingleResult(pointAvg);
    }
}
