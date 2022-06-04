package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.UserLoginRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.service.TokenService;
import com.spring.usinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spring.usinsa.dto.TokenDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminLoginController {

    private final UserService userService;
    private final TokenService tokenService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

/*    @PostMapping("/login")
    public void login1(@ModelAttribute UserLoginRequestDto userLoginRequestDto, HttpServletResponse response){
        try{
            log.info("test");
            TokenDto tokenDto = tokenService.login(userLoginRequestDto);
            System.out.println("test1");
            Cookie accessToken = new Cookie("accessToken", tokenDto.getAccessToken());
            Cookie refreshToken = new Cookie("refreshToken", tokenDto.getRefreshToken());
            accessToken.setMaxAge(60 * 3600);
            refreshToken.setMaxAge(60 * 3600);

            System.out.println(tokenDto.getAccessToken());
            response.addCookie(accessToken);
            response.addCookie(refreshToken);
        }catch (Exception e){
            log.info("admin/login, username: " + userLoginRequestDto.getUsername());
            log.info(e.getMessage());
        }
    }*/
}
