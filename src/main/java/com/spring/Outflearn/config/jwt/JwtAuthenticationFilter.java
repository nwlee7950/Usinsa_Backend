package com.spring.Outflearn.config.jwt;

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

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // Request 로 들어오는 Jwt Token 의 유효성을 검증 - JwtTokenProvider.validateToken() 을 filter 로서 filterChain 에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Request Header ("X-AUTH-TOKEN") 에서 token 가져오기
        String token = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        log.debug("JwtAuthenticationFilter token = {}", token);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            log.debug("token == null or jwtTokenProvider.validateToken(token) false");
        }

        filterChain.doFilter(request, response);
    }
}
