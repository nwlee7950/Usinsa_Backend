package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    Page<Review> findAllByProductId(long productId, Pageable pageable);
    void deleteById(Long id);

    @Query(value = "select avg(r.point) " +
            "from Review as r " +
            "where r.productId = :product_id "
            )
    Float getPointAvgByProductId(@Param("product_id") Long productId);
}
