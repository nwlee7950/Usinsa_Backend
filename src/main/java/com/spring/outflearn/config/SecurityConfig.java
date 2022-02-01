package com.spring.outflearn.config;

import com.spring.outflearn.config.jwt.JwtAuthenticationFilter;
import com.spring.outflearn.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/scss/**", "/vendor/**");       // 인증을 무시할 경로
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {     // 페이지 별 접근 권한 설정
        http
                .cors()
            .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()  // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그인 기능 비활성화
                .logout().disable()     // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그아웃 기능 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X

            .and()
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 넣는다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

                // 요청에 대한 사용권한 체크
                .authorizeRequests()
                .antMatchers("/**").permitAll();    // 누구나 접근 가능
//                .antMatchers(HttpMethod.POST, "/borrow/**").hasAnyRole("USER", "ADMIN")    // USER, ADMIN만 접근 가능
//                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN") // ADMIN만 접근 가능
//                .antMatchers(HttpMethod.POST, "/books/**").hasRole("USER") // USER만 접근 가능
//                .anyRequest().authenticated()  // 나머지는 권한이 있기만 하면 접근 가능
    }
}
