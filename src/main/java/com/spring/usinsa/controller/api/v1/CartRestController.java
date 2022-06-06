package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.CartDto;
import com.spring.usinsa.dto.product.CartResponseDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.CartService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartRestController {

    private final ApiResponseService apiResponseService;
    private final CartService cartService;

    @GetMapping
    public SingleResponse<List<CartResponseDto>> findByUserId(@AuthenticationPrincipal User user)
    {
        List<CartResponseDto> result = cartService.findByUserId(user.getId());

        return apiResponseService.getSingleResult(result);
    }

    @PostMapping
    public SingleResponse<CartResponseDto> save(@AuthenticationPrincipal User user, @RequestBody CartDto cartDto) {
        cartDto.setUserId(user.getId());

        return apiResponseService.getSingleResult(cartService.save(cartDto));
    }

    @GetMapping("/total")
    public SingleResponse<Integer> countByUserId(@AuthenticationPrincipal User user){
        int result = cartService.countByUserId(user.getId());

        return apiResponseService.getSingleResult(result);
    }
}
