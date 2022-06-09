package com.spring.usinsa.service;

import com.spring.usinsa.dto.inquiry.QnADto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.inquiry.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnAService {
    Qna save(QnADto.Request qnADto);
    QnADto.Response findById(Long qnaId, User user);
    Page<QnADto.Response> findByUserId(Long userId, Pageable pageable);
    Page<QnADto.Response> findAll(Pageable pageable);
    Qna findByIdAsEntity(Long id);
    void deleteQnaByQnAId(Long qnaId);
}
