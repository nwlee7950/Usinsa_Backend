package com.spring.usinsa.service;

import com.spring.usinsa.dto.PaymentCancelDto;
import com.spring.usinsa.dto.PaymentDto;
import com.spring.usinsa.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public interface PayService {
    String getToken() throws Exception;
    String savePayment(User user, PaymentDto.Request paymentDto) throws Exception;
    PaymentDto.Response updateByMerchantUid(PaymentDto.UpdateRequest paymentDto) throws Exception;
    void cancelPayment(PaymentCancelDto.Request paymentCancelDto) throws Exception;
    void webhookHandler(PaymentDto.WebhookRequest paymentDto) throws Exception;
}
