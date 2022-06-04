package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.SubCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    private static final String gender = "male";
    private static final int price = 19800;
    private static final String title = "021541 레이어드 반팔 티셔츠(2pck)";
    private static final long brandId = 1;
    private static final long subCategoryId = 1;

    @Test
    public void save(){
        // given
        Product product = Product.builder()
                .gender(gender)
                .price(price)
                .title(title)
                .brand(buildBrand())
                .subCategory(buildSubCategory()).build();

        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(savedProduct).isNotNull();
    }

    @Test
    public void findProduct(){
        // given
        Product product = Product.builder()
                .gender(gender)
                .price(price)
                .title(title)
                .brand(buildBrand())
                .subCategory(buildSubCategory()).build();
        Product savedProduct = productRepository.save(product);

        //when, find

        //and id
            Product findProduct = productRepository.findById(savedProduct.getId())
                    .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        //and brandId
        Page<Product> products = productRepository.findByBrandId(savedProduct.getBrand().getId(), PageRequest.of(0, 20));

        //and subCategory
        Page<Product> products2 = productRepository.findByBrandIdAndGender(savedProduct.getBrand().getId(), savedProduct.getGender(), PageRequest.of(0, 20));

        //and BrandIdAndSubCategory
        Page<Product> products3 = productRepository.findByBrandIdAndSubCategoryId(savedProduct.getBrand().getId(), savedProduct.getSubCategory().getId(), PageRequest.of(0, 20));

        //then
        assertThat(findProduct).isNotNull();
        assertThat(products).isNotNull();
        assertThat(products2).isNotNull();
        assertThat(products3).isNotNull();

        assertThat(findProduct.getId()).isEqualTo(savedProduct.getId());
        assertThat(products.getTotalElements()).isGreaterThan(0);
        assertThat(products2.getTotalElements()).isGreaterThan(0);
        assertThat(products3.getTotalElements()).isGreaterThan(0);
    }



    private Brand buildBrand(){
        return  Brand.builder()
                .title("테스트 브랜드")
                .enTitle("test brand")
                .image("image.png")
                .info("test").build();
    }

    private SubCategory buildSubCategory(){
        return SubCategory.builder()
                .title("테스트 서브 카테고리")
                .category(buildCategory())
                .build();
    }

    private Category buildCategory(){
        return Category.builder()
                .title("테스트 카테고리")
                .build();
    }

}
