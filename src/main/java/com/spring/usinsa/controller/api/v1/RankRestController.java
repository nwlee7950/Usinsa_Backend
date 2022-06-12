package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.RankRequestDto;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.RankService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rank")
public class RankRestController {

    private final ApiResponseService apiResponseService;
    private final RankService rankService;

    @GetMapping("/daily")
    public SingleResponse<Page<RankRequestDto>> findAllByDaily(Pageable pageable){
        Page<RankRequestDto> result = rankService.findAllByDaily(pageable);

        return apiResponseService.getSingleResult(result);
    }

    @GetMapping("/time")
    public SingleResponse<Page<RankRequestDto>> findAllByTime(Pageable pageable){
        Page<RankRequestDto> result = rankService.findAllByTime(pageable);

        return apiResponseService.getSingleResult(result);
    }
}
