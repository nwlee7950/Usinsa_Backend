package com.spring.usinsa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc       // MockMvc 빈 주입
//@RunWith(SpringRunner.class)    // JUnit4 사용으로 인해 붙여주는 어노테이션 (Junit5 에선 안붙여줘도 됨)
@SpringBootTest     // 실제 스프링 프레임워크를 띄워서 테스트함으로써 @Autowired 로 빈 주입이 가능하게 함
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello_is_returned() throws Exception {
        String hello = "Hello, World!";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}