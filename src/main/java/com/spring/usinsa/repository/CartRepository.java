package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Override
    Optional<Cart> findById(Long cartId);
    List<Cart> findByUserId(Long userId);
    int countByUserId(Long userId);
}
