package com.spring.usinsa.repository;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.Role;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)  // JUnit4 일때 사용
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final String username = "jae518";
    private static final String password = "wodus123";
    private static final String name = "김재연";
    private static final String nickname = "김재연 닉네임";
    private static final String email = "jae518@naver.com";
    private static final String phone = "010-8332-0930";

    @Test
    public void save() {
        // given
        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .email(email)
                .phone(phone)
                .social(Social.USINSA)
                .disable(false)
                .build();

        // when
        User saveResult = userRepository.save(user);

        // then
        assertThat(saveResult.getId()).isNotNull();
    }

    @Test
    public void find() {
        // given
        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .email(email)
                .phone(phone)
                .social(Social.USINSA)
                .disable(false)
                .build();

        // when
        userRepository.save(user);
        User findByUsernameAndSocialResult = userRepository.findByUsernameAndSocial("jae518", Social.USINSA)
                .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_USINSA_USER));
        User findFirstByEmailAndSocialResult = userRepository.findFirstByEmailAndSocial("jae518@naver.com", Social.USINSA)
                .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_USINSA_USER));
        User findFirstByEmailAndNameResult = userRepository.findFirstByEmailAndName("jae518@naver.com", "김재연")
                .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_USINSA_USER));
        User findFirstBySocialAndSocialIdResult = userRepository.findFirstBySocialAndSocialId(Social.USINSA, null)
                .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_USINSA_USER));

        Boolean existsByEmailResult = userRepository.existsByEmail("jae518@naver.com");
        Boolean existsByUsernameResult = userRepository.existsByUsername("jae518");
        Boolean existsBySocialAndSocialIdResult = userRepository.existsBySocialAndSocialId(Social.USINSA, null);

        // then
        assertThat(findByUsernameAndSocialResult).isNotNull();
        assertThat(findFirstByEmailAndSocialResult).isNotNull();
        assertThat(findFirstByEmailAndNameResult).isNotNull();
        assertThat(findFirstBySocialAndSocialIdResult).isNotNull();

        assertThat(existsByEmailResult).isTrue();
        assertThat(existsByUsernameResult).isTrue();
        assertThat(existsBySocialAndSocialIdResult).isTrue();
    }
}