package com.spring.usinsa.filter;

import com.spring.usinsa.util.JwtTokenProvider;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // Request 로 들어오는 Jwt Token 의 유효성을 검증 - JwtTokenProvider.validateToken() 을 filter 로서 filterChain 에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Request Header ("X-AUTH-TOKEN") 에서 Access Token 가져오기
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        // Request Header ("REFRESH-TOKEN") 에서 Refresh Token 가져오기
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);

        // Token 없이 요청해야 하는 url 일 경우 (ex) 로그인 / 회원가입 / 비밀번호 찾기 등
        if(!StringUtils.hasText(accessToken) && !StringUtils.hasText(refreshToken)) {
            log.debug("Access Token and Refresh Token are null.");
        }
        else {
            // Access Token 유효성 검사
            if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 유효기간이 만료된 Access Token 일 경우, Refresh Token 을 통한 재발급이 필요하므로 Refresh Token 유효성 검사
            else if(StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
                log.debug("Access Token is invalid, but Refresh Token is valid.");
                throw new ApiException(ApiErrorCode.INVALID_TOKEN);
            }
            // 유효기간이 만료된 Access Token 만 있을 경우, Client 에 Refresh Token 을 같이 보내달라고 요청하는 예외 처리.
            else {
                throw new ApiException(ApiErrorCode.INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request, response);
    }
}
