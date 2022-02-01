package com.spring.outflearn.config.jwt;

import com.spring.outflearn.exception.ApiErrorCode;
import com.spring.outflearn.exception.ApiException;
import com.spring.outflearn.model.User;
import com.spring.outflearn.serviceImpl.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;
    private final String ROLES = "roles";
    private final String BEARER = "bearer";
    private final long accessTokenValidMilisecond = 1000L * 60 * 30; // 30분 Access Token 유효
    private final long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24 * 30; // 30일 Refresh Token 유효

    private final CustomUserDetailsService userDetailsService;

    // Jwt 생성 시 서명으로 사용할 secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 DTO 생성
    public TokenDto createTokenDto(long userPk, List<String> roles) {
        System.out.println("secretKey = " + secretKey);
        // Claims 에 user 구분을 위한 User pk(userId) 및 authorities 목록 삽입
        Claims claims = Jwts.claims().setSubject(Long.toString(userPk));
        claims.put(ROLES, roles);

        // 토큰 생성날짜, 만료날짜를 위한 Date
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER) // 데이터
                .accessToken(accessToken) // 토큰 발행일자
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMilisecond)
                .build();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {

        // Jwt 에서 claims 추출
        Claims claims = parseClaims(token);

        // 권한 정보가 없을 때
        if(claims.get(ROLES) == null) {
            throw new ApiException(ApiErrorCode.TOKEN_ROLE_EMPTY);
        }

        User customUser = (User) userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(customUser, "", customUser.getAuthorities());
    }

    // Jwt 토큰 복호화
    public Claims parseClaims(String token) {
        try {
            // 만료된 토큰이어도 refresh token 검증 후 재발급할 수 있도록 claims 반환
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.toString());
            return e.getClaims();
        }
    }

    // Request 의 Header 에서 token 파싱 : "X-AUTH-TOKEN: Access Token"
    public String resolveAccessToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Request 의 Header 에서 token 파싱 : "REFRESH-TOKEN: Refresh Token"
    public String resolveRefreshToken(HttpServletRequest req) {
        return req.getHeader("REFRESH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("expired Token !" , e);
            return false;
        } catch (Exception e) {
            log.error("invalid Token !" , e);
            return false;
        }
    }
}
