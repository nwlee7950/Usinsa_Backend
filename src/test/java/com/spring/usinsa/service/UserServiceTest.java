package com.spring.usinsa.service;

import com.spring.usinsa.model.Role;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import com.spring.usinsa.repository.UserRepository;
import com.spring.usinsa.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*

Mockito : 개발자가 동작을 직접 제어할 수 있는 가짜(Mock) 객체를 지원하는 테스트 프레임워크.
여러 객체들 간의 의존성은 단위 테스트를 작성하는 것을 어렵게 하는데, 이를 해결하기 위해 가짜 객체를 주입시켜주는 Mockito 라이브러리를 활용.
Mockito를 활용함으로써 가짜 객체에 원하는 결과를 Stub하여 단위 테스트를 진행.(물론 Mock을 하지 않아도 된다면 하지 않는 것이 더욱 좋다.)

@Mock: Mock 객체를 만들어 반환해주는 어노테이션
@Spy: Stub하지 않은 메소드들은 원본 메소드 그대로 사용하는 어노테이션
@InjectMocks: @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입시켜주는 어노테이션

 */
//@RunWith(MockitoJUnitRunner.class)    // JUnit4 일때 사용
@ExtendWith(MockitoExtension.class)     // JUnit5 일때 사용
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;



    private static final String username = "jae518";
    private static final String password = "wodus123";
    private static final String name = "김재연";
    private static final String nickname = "김재연 닉네임";
    private static final String email = "jae518@naver.com";
    private static final String phone = "010-8332-0930";

    @Test
    void findFirstByUsernameAndSocial() {
    }

    @Test
    void findFirstByEmailAndName() {
    }

    @Test
    void findFirstBySocialAndSocialId() {
    }

    @Test
    void findById() {
    }

    @DisplayName("회원 가입")
    @Test
    void signUp() {
        // given
        doReturn(false).when(userRepository).existsByEmail(email);   // userRepository 에서 existsByEmail 수행 시, 무조건 false 반환
        doReturn(false).when(userRepository).existsByUsername(username);   // userRepository 에서 existsByUsername 수행 시, 무조건 false 반환
        doReturn(buildUser()).when(userRepository).save(any(User.class));   // userRepository 에서 save 수행 시, buildUser()이 반환하는 User 객체 반환

        // when
        final User result = userService.signUp(buildUser());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getSocial()).isEqualTo(Social.USINSA);

        // verify
        verify(userRepository, times(1)).save(any(User.class));     // userRepository 에서 save 수행이 1번 이루어진 게 맞는지 검증
        verify(userRepository, times(1)).existsByEmail(email);     // userRepository 에서 existsByEmail 수행이 1번 이루어진 게 맞는지 검증
        verify(userRepository, times(1)).existsByUsername(username);     // userRepository 에서 existsByUsername 수행이 1번 이루어진 게 맞는지 검증
    }

    @Test
    void resetPassword() {
    }

    private User buildUser() {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .email(email)
                .phone(phone)
                .social(Social.USINSA)
                .disable(false)
                .build();
    }

    private User buildUser2() {
        return User.builder()
                .username(username + "2")
                .password(passwordEncoder.encode(password))
                .name(name + "2")
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .email(email + "2")
                .phone(phone)
                .social(Social.USINSA)
                .disable(false)
                .build();
    }
}