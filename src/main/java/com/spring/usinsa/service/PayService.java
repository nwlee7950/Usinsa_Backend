package com.spring.usinsa.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface PayService {
    String getToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
