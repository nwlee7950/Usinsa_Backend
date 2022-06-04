package com.spring.usinsa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.repository.customRepository.BrandRepositoryCustom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandRepositoryCustom brandRepositoryCustom;

    @Autowired
    private JPAQueryFactory queryFactory;

    private static String title = "나이키";
    private static String enTitle = "nike";
    private static String info = " 브랜드 소개글 ";
    private static String image = "image.png";

    private static String title2 = "아디다스";
    private static String enTitle2 = "adidas";
    private static String info2 = " 브랜드 소개글 2 ";
    private static String image2 = "image2.png";

    @Test
    @Transactional(  )
    public void save(){
        // given
        Brand brand = buildBrand();

        // when
        Brand saveBrand = brandRepository.save(brand);

        // then
        assertThat(saveBrand.getId()).isNotNull();
    }

    @Test
    public void findById(){
        // given
        Brand brand = buildBrand();
        brandRepository.save(brand);

        //when
        Brand findBrand;
        try {
            findBrand = brandRepository.findById(brand.getId())
                    .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));
            assertThat(findBrand).isNotNull();
            assertThat(findBrand.getId()).isEqualTo(brand.getId());
        }catch(ApiException e) {
            System.out.println("브랜드 찾기 실패");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void findBrandDtoByTitle(){
        // given
        brandRepository.save(buildBrand());
        brandRepository.save(buildBrand2());

        //when
        List<Brand> brandList = brandRepository.findByTitleContainsOrEnTitleContains("ad", "ad");
        List<Brand> brandList1 = brandRepository.findByTitleContainsOrEnTitleContains("나", "나");

        //then
        assertThat(brandList).isNotNull();
        assertThat(brandList.size()).isGreaterThan(0);
        assertThat(brandList1).isNotNull();
        assertThat(brandList1.size()).isGreaterThan(0);
    }

    @DisplayName("서브 카테고리로 검색 시 해당 카테고리 제품을 팔고 있는 브랜드 검색")
    @Test
    public void findBrandListBySubCategory(){
        //given, category, product가 있다는 전제
        brandRepository.save(buildBrand());

        //when
        List<BrandBySubCategoryDto> brandList = brandRepository.findBrandListBySubCategory(1l);

        //then
        assertThat(brandList).isNotNull();
        assertThat(brandList.size()).isGreaterThan(0);
    }

    @Test
    public void findAll(){
        //given
        brandRepository.save(buildBrand());
        brandRepository.save(buildBrand2());

        //when
        Page<Brand> brandPage = brandRepository.findAll(PageRequest.of(0, 20));

        //then
        assertThat(brandPage).isNotNull();
        assertThat(brandPage.getTotalElements()).isGreaterThan(0);
    }

    @Test
    public void querydslTest(){
        //when
        List<BrandBySubCategoryDto> result = brandRepositoryCustom.findBrandListBySubCategory(1l);


        //then
        System.out.println(result.size());
        assertThat(result).isNotNull();
    }

    private Brand buildBrand(){
        return  Brand.builder()
                .title(title)
                .enTitle(enTitle)
                .image(image)
                .info(info).build();
    }

    private Brand buildBrand2(){
        return  Brand.builder()
                .title(title2)
                .enTitle(enTitle2)
                .image(image2)
                .info(info2).build();
    }

}