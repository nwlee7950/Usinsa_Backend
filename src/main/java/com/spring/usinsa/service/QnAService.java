package com.spring.usinsa.service;

import com.spring.usinsa.dto.inquiry.QnADto;
import com.spring.usinsa.model.inquiry.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnAService {
    Qna save(QnADto.Request qnADto, long userId);
    Page<Qna> findByUserId(long userId, Pageable pageable);
    void deleteQnaByQnAId(long qnaId);
    Page<Qna> findAll(Pageable pageable);

//    QnaDto updateQnA(QnADto);

}
