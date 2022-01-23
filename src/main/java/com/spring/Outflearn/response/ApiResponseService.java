package com.spring.Outflearn.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiResponseService {
    // 단일건 결과 처리 메소드
    public <T> SingleResponse<T> getSingleResult(T data) {
        SingleResponse<T> result = new SingleResponse<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 복수건 결과 처리 메서드
    public <T> ListResponse<T> getListResult(List<T> list) {
        ListResponse<T> result = new ListResponse<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과만 처리
    public CommonResponse getSuccessResult() {
        CommonResponse result = new CommonResponse();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리
    public CommonResponse getFailResult() {
        CommonResponse result = new CommonResponse();
        setFailResult(result);
        return result;
    }

    // API 요청 성공 시 응답 모델을 성공 데이터로 세팅
    private void setSuccessResult(CommonResponse result) {
        result.setSuccess(true);
        result.setCode(CommonResponseConstant.SUCCESS.getCode());
        result.setMsg(CommonResponseConstant.SUCCESS.getMsg());
    }

    // API 요청 실패 시 응답 모델을 실패 데이터로 세팅
    private void setFailResult(CommonResponse result) {
        result.setSuccess(false);
        result.setCode(CommonResponseConstant.FAIL.getCode());
        result.setMsg(CommonResponseConstant.FAIL.getMsg());
    }

    public CommonResponse getFailResult(String message) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(CommonResponseConstant.FAIL.getCode());
        result.setMsg(message);
        return result;
    }

    public CommonResponse getFailResult(String message, int statusCode) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(statusCode);
        result.setMsg(message);
        return result;
    }
}
