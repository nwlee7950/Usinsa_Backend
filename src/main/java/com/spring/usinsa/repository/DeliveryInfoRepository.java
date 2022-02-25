package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
    Optional<DeliveryInfo> findById(long id);
}
