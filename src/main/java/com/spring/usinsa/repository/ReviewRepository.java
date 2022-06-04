package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable);
    Page<Review> findByUserId(Long userId, Pageable pageable);

    @Query("UPDATE Review r set r.nickname = :nickname where r.userId = :userId ")
    void updateNicknameByUserId(@Param("nickname") String nickname, @Param("userId") Long userId);


    void deleteById(Long id);
}
