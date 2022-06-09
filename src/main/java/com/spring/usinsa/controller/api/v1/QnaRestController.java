package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.inquiry.QnACategoryDto;
import com.spring.usinsa.dto.inquiry.QnADto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.QnACategoryService;
import com.spring.usinsa.service.QnAService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/qna")
public class QnaRestController {

    private final ApiResponseService apiResponseService;
    private final QnAService qnAService;
    private final QnACategoryService qnACategoryService;

    @GetMapping
    public SingleResponse<Page<QnADto.Response>> findByUserId(@AuthenticationPrincipal User user, Pageable pageable){

        return apiResponseService.getSingleResult(qnAService.findByUserId(user.getId(), pageable));
    }

    @GetMapping("/category")
    public SingleResponse<List<QnACategoryDto>> findAll(){
        List<QnACategoryDto> result =  qnACategoryService.findAll();

        return apiResponseService.getSingleResult(result);
    }

    @PostMapping
    public CommonResponse save(@AuthenticationPrincipal User user, @RequestBody QnADto.Request qnaDto){
        qnaDto.setUserId(user.getId());
        qnAService.save(qnaDto);

        return apiResponseService.getSuccessResult();
    }

    @GetMapping("/detail/{id}")
    public SingleResponse<QnADto.Response> findById(@AuthenticationPrincipal User user, @PathVariable Long id){

        return apiResponseService.getSingleResult(qnAService.findById(id, user));
    }
}
