package com.spring.usinsa.repository;


import com.spring.usinsa.model.product.Category;
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
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    private String title = "상의";
    private long savedId = 0;

    @Test
    public void save(){
        Category category = categoryRepository.save(Category.builder()
                .title(title).build());

        assertThat(category.getId()).isNotNull();
        savedId = category.getId();
    }

    @Test
    public void find(){
        Category category = categoryRepository.getById(savedId);

        assertThat(category.getId()).isEqualTo(savedId);
    }


}
