package com.spring.usinsa.repository;


import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.dto.product.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findById(Long id);

    @Query(value = "select new com.spring.usinsa.dto.product.BrandDto.Response(b.id, b.title, b.en_title, count(*)) " +
            " from Brand b join Product p on b.id = p.brand_id " +
            " where b.title like '%:title%' or b.en_title like '%:title%' group by p.brand_id", nativeQuery = true)
    List<BrandDto.Response> getBrandDtoByTitle(@Param("title") String title);

    @Query(value = "select new com.spring.usinsa.dto.product.BrandDto.ResponseBySubCategory(b.id, count(sub_category_id), b.title, p.sub_category_id ) " +
            " from Brand b join Product p on b.id = p.brand_id" +
            " where p.sub_category_id = :sub_category_id group by b.brand_id", nativeQuery = true)
    List<BrandDto.ResponseBySubCategory> findBrandListBySubCategory(@Param("sub_category_id") long subCategoryId);

}
