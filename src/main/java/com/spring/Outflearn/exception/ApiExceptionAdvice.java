package com.spring.Outflearn.exception;

import com.spring.Outflearn.response.ApiResponseService;
import com.spring.Outflearn.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = {"com.spring.Outflearn.controller.api.v1"})
@Slf4j
public class ApiExceptionAdvice {
    protected final ApiResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<CommonResponse> defaultException(HttpServletRequest request, Exception e) {
        log.error("RestControllerAdvice Exception, url : {}", request.getRequestURL(), e);
        ResponseEntity<CommonResponse> responseEntity;
        if(e instanceof MissingServletRequestParameterException){
            responseEntity = ResponseEntity.ok(responseService.getFailResult("invalid_params"));
        }
        else if(e instanceof ApiException){
            ApiException apiException = (ApiException) e;
            ApiErrorCode apiErrorCode = apiException.getErrorCode();
            responseEntity = ResponseEntity.ok(
                    responseService.getFailResult(apiErrorCode.getMessage(), apiErrorCode.getStatusCode())
            );
        }
        else if(e instanceof DataIntegrityViolationException) {
            log.error(e.toString());
            ApiException apiException = new ApiException(ApiErrorCode.SQL_INVALID_QUERY);
            ApiErrorCode apiErrorCode = apiException.getErrorCode();
            responseEntity = ResponseEntity.ok(
                    responseService.getFailResult(apiErrorCode.getMessage(), apiErrorCode.getStatusCode())
            );
        }
        else if(e instanceof MessagingException) {
            log.error(e.toString());
            ApiException apiException = new ApiException(ApiErrorCode.MAIL_SEND_FAIL);
            ApiErrorCode apiErrorCode = apiException.getErrorCode();
            responseEntity = ResponseEntity.ok(
                    responseService.getFailResult(apiErrorCode.getMessage(), apiErrorCode.getStatusCode())
            );
        }
        else if(e instanceof ConstraintViolationException) {
            log.error(e.toString());
            ApiException apiException = new ApiException(ApiErrorCode.INVALID_ENTITY_FIELD);
            ApiErrorCode apiErrorCode = apiException.getErrorCode();
            responseEntity = ResponseEntity.ok(
                    responseService.getFailResult(apiErrorCode.getMessage(), apiErrorCode.getStatusCode())
            );
        }
        else{
            responseEntity = ResponseEntity.ok(responseService.getFailResult(e.getMessage()));
        }

        return responseEntity;
    }

}