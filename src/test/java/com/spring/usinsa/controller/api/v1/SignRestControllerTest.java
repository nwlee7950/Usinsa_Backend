package com.spring.usinsa.controller.api.v1;

import com.google.gson.Gson;
import com.spring.usinsa.config.jwt.JwtTokenProvider;
import com.spring.usinsa.dto.UserLoginRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.repository.RefreshTokenRepository;
import com.spring.usinsa.repository.UserRepository;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import com.spring.usinsa.serviceImpl.CustomUserDetailsService;
import com.spring.usinsa.serviceImpl.TokenServiceImpl;
import com.spring.usinsa.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   // Mock 빈 주입
@ExtendWith(MockitoExtension.class)
class SignRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @InjectMocks
    private SignRestController signRestController;
    @Mock
    private UserService userService;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private ApiResponseService apiResponseService;
    @Mock
    private TokenServiceImpl tokenService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    private static final String username = "jae518";
    private static final String password = "wodus123";
    private static final String name = "김재연";
    private static final String nickname = "김재연 닉네임";
    private static final String email = "jae518@naver.com";
    private static final String phone = "010-8332-0930";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(signRestController)
                .build();
    }

    @DisplayName("회원가입")
    @Test
    public void signup() throws Exception {
        // given
        String url = "/api/v1/signup";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(signupRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
        );


        // then
        resultActions.andExpect(status().isOk());
//
//        final SingleResponse<TokenDto> response = gson.fromJson(resultActions.andReturn()
//                .getResponse()
//                .getContentAsString(StandardCharsets.UTF_8), MembershipResponse.class);
//
//        assertThat(response.getMembershipType()).isEqualTo(MembershipType.NAVER);
//        assertThat(response.getId()).isNotNull();

    }


    private UserLoginRequestDto signinRequest() {
        return UserLoginRequestDto.builder()
                .username("jae518")
                .password("wodus123")
                .build();
    }

    private UserSignUpRequestDto signupRequest() {
        return UserSignUpRequestDto.builder()
                .username(username)
                .password(password)
                .name(name)
                .nickname(nickname)
                .email(email)
                .phone(phone)
                .build();
    }
}