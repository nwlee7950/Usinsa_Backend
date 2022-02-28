package com.spring.usinsa.filter;

import com.spring.usinsa.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;



@Slf4j
@Component
@RequiredArgsConstructor
public class AdminFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal != "anonymousUser") {
                User admin = (User) principal;
                request.setAttribute("user", admin);
            }
        } catch (NullPointerException ex) {}

        chain.doFilter(request, response);
    }
}