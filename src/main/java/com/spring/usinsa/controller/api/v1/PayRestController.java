package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.PaymentCancelDto;
import com.spring.usinsa.dto.PaymentDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.PayService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pay")
public class PayRestController {
    private final PayService payService;
    private final ApiResponseService apiResponseService;

    @GetMapping("/getToken")
    public SingleResponse<String> getToken() throws Exception{
        String token = payService.getToken();

        return apiResponseService.getSingleResult(token);
    }

    @PostMapping("/payments")
    public SingleResponse<String> savePayment(@AuthenticationPrincipal User user, PaymentDto.Request paymentDto) throws Exception{
        paymentDto.setUserId(user.getId());
        String merchantUid  = payService.savePayment(user, paymentDto);

        return apiResponseService.getSingleResult(merchantUid);
    }

    @PutMapping("/payments")
    public SingleResponse<PaymentDto.Response> updateByMerchantUid(PaymentDto.UpdateRequest paymentDto) throws Exception{
        PaymentDto.Response response = payService.updateByMerchantUid(paymentDto);

        return apiResponseService.getSingleResult(response);
    }

    @PostMapping
    public CommonResponse webhookHandler(PaymentDto.WebhookRequest paymentDto) throws Exception{
        payService.webhookHandler(paymentDto);

        return apiResponseService.getSuccessResult();
    }

    @PostMapping("/payments/cancel")
    public CommonResponse cancelPayment(PaymentCancelDto.Request paymentCancelDto) throws Exception{
        payService.cancelPayment(paymentCancelDto);

        return apiResponseService.getSuccessResult();
    }
}
