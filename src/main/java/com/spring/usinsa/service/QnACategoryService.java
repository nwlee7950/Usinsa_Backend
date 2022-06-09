package com.spring.usinsa.service;

import com.spring.usinsa.dto.inquiry.QnACategoryDto;
import com.spring.usinsa.model.inquiry.QnaCategory;

import java.util.List;

public interface QnACategoryService {
    QnaCategory findById(Long id);
    List<QnACategoryDto> findAll();
}
