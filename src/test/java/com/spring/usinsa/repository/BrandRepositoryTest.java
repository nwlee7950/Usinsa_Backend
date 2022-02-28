package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    private static String title = "아디다스";
    private static String enTitle = "adidas";
    private static String info = " 삼성보다 삼선 ";
    private static String image = "image1.png";
    private long savedId = 0;

    @Test
    public void save(){
        Brand brand = Brand.builder()
                .title(title)
                .enTitle(enTitle)
                .image(image)
                .info(info).build();

        Brand saveBrand =brandRepository.save(brand);

        assertThat(saveBrand.getId()).isNotNull();
    }

    @Test
    public void find(){
        Brand brand = brandRepository.findById(savedId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        assertThat(brand.getId()).isEqualTo(savedId);
    }

    @Test
    public void findBrandDtoByTitle(){
        List<BrandDto.Response> brandList = brandRepository.getBrandDtoByTitle("ad");
        assertThat(brandList).isNotNull();


        List<BrandDto.Response> brandList1 = brandRepository.getBrandDtoByTitle("디다");
        assertThat(brandList1).isNotNull();
    }

    @DisplayName("서브 카테고리로 검색 시 해당 카테고리 제품을 팔고 있는 브랜드 검색")
    @Test
    public void findBrandListBySubCategory(){
        List<BrandDto.ResponseBySubCategory> brandList = brandRepository.findBrandListBySubCategory(1);
        assertThat(brandList).isNotNull();

        List<BrandDto.ResponseBySubCategory> brandList1 = brandRepository.findBrandListBySubCategory(2);
        assertThat(brandList1).isNull();
    }
}