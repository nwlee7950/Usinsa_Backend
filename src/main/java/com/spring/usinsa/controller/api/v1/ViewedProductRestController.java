package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.ViewedProductDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.ViewedProductService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/viewed")
public class ViewedProductRestController {

    private final ViewedProductService viewedProductService;
    private final ApiResponseService apiResponseService;

    @GetMapping
    public SingleResponse<List<ViewedProductDto>> findByUserId(@AuthenticationPrincipal User user){

        List<ViewedProductDto> result = viewedProductService.findByUserId(user.getId());

        return apiResponseService.getSingleResult(result);
    }

    @PostMapping("/{id}")
    public SingleResponse<String> save(@AuthenticationPrincipal User user, @PathVariable Long id){

        viewedProductService.save(user.getId(), id);

        return apiResponseService.getSingleResult("success");
    }

}
