package com.spring.usinsa.service;


import com.spring.usinsa.dto.inquiry.QnAAnswerDto;
import com.spring.usinsa.model.inquiry.QnaAnswer;

public interface QnaAnswerService {

    void save(QnAAnswerDto qnAAnswerDto);
    QnAAnswerDto findById(Long id);
    Boolean existsById(Long id);
}
