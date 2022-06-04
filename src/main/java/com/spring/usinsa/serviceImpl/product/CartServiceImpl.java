package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.CartDto;
import com.spring.usinsa.dto.product.CartResponseDto;
import com.spring.usinsa.model.product.Cart;
import com.spring.usinsa.repository.CartRepository;
import com.spring.usinsa.service.CartService;
import com.spring.usinsa.service.ProductService;
import com.spring.usinsa.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    @Override
    public CartResponseDto save(CartDto cartDto) {
        return CartResponseDto.toCartResponseDto(
                cartRepository.save(
                        cartDto.toCartEntity(
                                productService.findByIdAsEntity(cartDto.getProductId()),
                                productSizeService.findById(cartDto.getProductSizeId())
                        )
                )
        );
    }

    @Override
    public List<CartResponseDto> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(CartResponseDto::toCartResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public int countByUserId(Long userId) {
        return cartRepository.countByUserId(userId);
    }

    @Override
    public void deleteById(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
