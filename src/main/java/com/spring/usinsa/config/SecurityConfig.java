package com.spring.usinsa.config;

import com.spring.usinsa.filter.AdminFilter;
import com.spring.usinsa.filter.JwtAuthenticationFilter;
import com.spring.usinsa.filter.JwtExceptionFilter;
import com.spring.usinsa.util.JwtTokenProvider;
import com.spring.usinsa.serviceImpl.AdminUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity  // Spring Security 설정 활성화
@Configuration
public class SecurityConfig{

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // ADMIN
    @Order(0)
    @Configuration
    @RequiredArgsConstructor
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        private final AdminUserDetailsService adminUserDetailsService;

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/scss/**", "/vendor/**");       // 인증을 무시할 경로
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**")
                    .cors().and().csrf().disable()
                    .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/admin/brand")
                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login")
                    .invalidateHttpSession(true) // 로그아웃 시 저장해둔 세션 날리기
                    .and()
                    .authorizeRequests()    // 요청에 대한 사용권한 체크
                    .antMatchers("/admin/login").permitAll()
//                    .antMatchers("/**").hasAnyRole("ADMIN", "ROLE_SUPER_ADMIN")
                    .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .and()
                    .addFilterAfter(new AdminFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminUserDetailsService);
        }

    }

    // OAuth2
    @Order(1)
    @Configuration
    @RequiredArgsConstructor
    public static class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/scss/**", "/vendor/**");       // 인증을 무시할 경로
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/v1/oauth/**")
                    .cors()
                    .and()
                    .csrf().disable()
                    .httpBasic().disable()

                    .formLogin().disable()  // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그인 기능 비활성화
                    .logout().disable()     // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그아웃 기능 비활성화
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X
                    .and()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/");
        }
    }

    // API
    @Order(2)
    @Configuration
    @RequiredArgsConstructor
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtTokenProvider jwtTokenProvider;

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/scss/**", "/vendor/**");       // 인증을 무시할 경로
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {     // 페이지 별 접근 권한 설정
            http
                    .antMatcher("/api/v1/**")
                    .cors()
                    .and()
                    .csrf().disable()
                    .httpBasic().disable()

                    .formLogin().disable()  // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그인 기능 비활성화
                    .logout().disable()     // JWT 연동(검증)을 위해 Spring Security에서 제공하는 로그아웃 기능 비활성화
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X

                    .and()
                    .authorizeRequests()    // 요청에 대한 사용권한 체크
                    .antMatchers("/**").permitAll()
/*                    .antMatchers("/api/v1/sign-in", "/api/v1/sign-up-step-one",
                            "/api/v1/find-username", "/api/v1/find-password",
                            "/api/v1/verify-code", "/api/v1/reset-password",
                            "/api/v1/images", "/api/v1/register-routes",
                            "/api/v1/oauth/**", "/api/v1/auth/refresh-token").permitAll() */
                    .anyRequest().authenticated()  // 나머지는 권한이 있기만 하면 접근 가능
                    // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 넣는다.
                    .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        }
    }
}
