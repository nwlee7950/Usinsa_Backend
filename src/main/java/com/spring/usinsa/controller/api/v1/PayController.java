package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.PayService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pay")
public class PayController {
    private final PayService payService;
    private final ApiResponseService apiResponseService;

    @GetMapping("/getToken")
    public SingleResponse<String> getToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String token = payService.getToken(request, response);

        return apiResponseService.getSingleResult(token);
    }
}
