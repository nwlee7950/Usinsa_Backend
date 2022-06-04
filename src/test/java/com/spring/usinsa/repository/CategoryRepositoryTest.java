package com.spring.usinsa.repository;


import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    private String title = "category test";

    @Test
    public void save(){
        //given
        String categoryTitle = title;
        Category category = buildCategory(categoryTitle);

        //when
        Category savedCategory = categoryRepository.save(category);

        //then
        assertThat(savedCategory).isNotNull();
    }

    @Test
    public void findById(){
        //given
        Category category = categoryRepository.save(buildCategory(title));

        //when
        try {
            Category findCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

            //then
            assertThat(findCategory).isNotNull();

        }catch (ApiException e){
            System.out.println(e.getErrorCode().getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void findAll(){
        //given
        categoryRepository.save(buildCategory(title));

        //when
        List<Category> categorys = categoryRepository.findAll();

        assertThat(categorys).isNotNull();
        assertThat(categorys.size()).isGreaterThan(0);
    }

    @Test
    public void findByTitle(){
        //given
        categoryRepository.save(buildCategory(title));

        //when
        try {
            Category category = categoryRepository.findByTitle(title)
                    .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

            //then
            assertThat(category.getTitle()).isEqualTo(title);
        }catch (ApiException e){
            System.out.println(e.getErrorCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void existsByTitle(){
        //given
        categoryRepository.save(buildCategory(title));

        //when
        Boolean existsTitle = categoryRepository.existsByTitle(title);

        //then
        assertThat(existsTitle).isEqualTo(true);
    }

    private Category buildCategory(String title){
        return Category.builder()
                .title(title)
                .build();
    }
}
