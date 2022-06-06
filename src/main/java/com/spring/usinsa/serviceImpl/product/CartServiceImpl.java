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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    /**
     * 장바구니에 이미 담겨있으면 total + , 없다면 저장
     * @param cartDto
     * @return
     */
    @Override
    @Transactional
    public CartResponseDto save(CartDto cartDto) {
        System.out.println(cartDto.getProductId());
        System.out.println(cartDto.getProductSizeId());
        System.out.println(cartDto.getUserId());

        // 이미 장바구니에 담겨있는지 확인
        // todo, count를 사용하였지만 추후 성능 최적화를 위해 exists 또는 limit 1 으로 변경 필요
        if(cartRepository.existsByUserIdAndProductSizeId(cartDto.getUserId(), cartDto.getProductSizeId()) > 0){
            Cart cart = cartRepository.findByUserIdAndProductSizeId(cartDto.getUserId(), cartDto.getProductSizeId());
            cart.setTotal(cart.getTotal()+ cartDto.getTotal());

            return CartResponseDto.toCartResponseDto(cart);
        }else{
            return CartResponseDto.toCartResponseDto(
                    cartRepository.save(
                            cartDto.toCartEntity(
                                    productService.findByIdAsEntity(cartDto.getProductId()),
                                    productSizeService.findById(cartDto.getProductSizeId())
                            )
                    )
            );
        }
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
