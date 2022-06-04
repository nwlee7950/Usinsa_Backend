package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.model.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{

    Optional<Brand> findById(Long id);

    List<Brand> findByTitleContainsOrEnTitleContains(String title, String enTitle);

    @Query("select new com.spring.usinsa.dto.product.BrandBySubCategoryDto(b.id, b.title, count(*) ) " +
            " from Product p join Brand b on p.brand.id = b.id " +
            " where p.subCategory.id = :sub_category_id group by b.id")
    List<BrandBySubCategoryDto> findBrandListBySubCategory(@Param("sub_category_id") Long subCategoryId);
}
