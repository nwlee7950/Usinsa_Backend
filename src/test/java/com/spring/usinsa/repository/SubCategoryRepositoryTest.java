package com.spring.usinsa.repository;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.SubCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class SubCategoryRepositoryTest {

    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    CategoryRepository categoryRepository;


    private static long categoryId = 1;
    private static String title = "반팔티";
    private long savedId = 0;


    @Test
    public void save(){
        SubCategory subCategory = SubCategory.builder()
                .category(categoryRepository.findById(categoryId).orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND)))
                .title(title).build();

        SubCategory subCategory1 = SubCategory.builder()
                .category(categoryRepository.findById(categoryId).orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND)))
                .title(title).build();


        SubCategory saveSubCategory = subCategoryRepository.save(subCategory);
        SubCategory saveSubCategory1 = subCategoryRepository.save(subCategory1);
        assertThat(saveSubCategory.getId()).isNotNull();
        assertThat(saveSubCategory1.getId()).isNotNull();

        savedId = saveSubCategory.getId();
    }

    @Test
    public void find(){
        SubCategory subCategory = subCategoryRepository.findById(savedId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        assertThat(subCategory.getId()).isEqualTo(savedId);

        List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(categoryId);

        assertThat(subCategoryList).isNotNull();
    }

}
