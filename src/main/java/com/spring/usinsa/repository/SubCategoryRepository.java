package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.SubCategoryByBrandIdDto;
import com.spring.usinsa.model.product.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findById(Long id);
    List<SubCategory> findByCategoryId(Long categoryId);
    void deleteById(Long id);
    Optional<SubCategory> findByTitle(String title);
    Boolean existsByTitle(String title);

    @Query("select new com.spring.usinsa.dto.product.SubCategoryByBrandIdDto(s.id, s.title, count(*) ) " +
            " from SubCategory s join Product p on p.subCategory.id = s.id " +
            " where p.brand.id = :brand_id group by s.id")
    List<SubCategoryByBrandIdDto> findByBrandId(@Param("brand_id") Long brandId);
}
