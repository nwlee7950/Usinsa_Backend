package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findById(long id);
    List<SubCategory> findByCategoryId(long categoryId);
    long deleteById(long id);
}
