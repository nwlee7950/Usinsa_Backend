package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Product;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private DeliveryInfoRepository deliveryInfoRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    private static final String gender = "male";
    private static final int price = 19800;
    private static final String title = "021541 레이어드 반팔 티셔츠(2pck)";
    private static final long brandId = 1;
    private static final long deleveryInfoId = 1;
    private static final long subCategoryId = 1;



    @Test
    public void save(){
        // given
        Product product = Product.builder()
                .gender(gender)
                .price(price)
                .title(title)
                .brand(brandRepository.findById(brandId).orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND)))
                .deliveryInfo(deliveryInfoRepository.findById(deleveryInfoId).orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND)))
                .subCategory(subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND))).build();

        //when
        Product productResult = productRepository.save(product);

        //then
        assertThat(productResult.getId()).isNotNull();
    }

    @Test
    public void findProduct(){
        // given
        ProductDto.FindProductRequest productRequestDto = ProductDto.FindProductRequest.builder()
                .subCategoryId(subCategoryId)
                .brandId(brandId)
                .sort("price")
                .price1(0)
                .price2(100000).build();

        Page<Product> products = productRepository.findByBrandId(brandId, PageRequest.of(1, 20));
        Page<Product> products2 = productRepository.findByBrandIdAndGender(brandId, gender, PageRequest.of(1, 20));
        Page<Product> products3 = productRepository.findByBrandIdAndSubCategoryId(brandId, subCategoryId, PageRequest.of(1, 20));

        assertThat(products.getTotalElements()).isNotNull();
//        assertThat(products.get)

    }


}
