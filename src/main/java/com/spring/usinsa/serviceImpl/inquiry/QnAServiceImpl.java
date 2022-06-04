package com.spring.usinsa.serviceImpl.inquiry;

import com.spring.usinsa.dto.inquiry.QnADto;
import com.spring.usinsa.model.inquiry.Qna;
import com.spring.usinsa.repository.QnARepository;
import com.spring.usinsa.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnAServiceImpl implements QnAService {

    QnARepository qnARepository;

    @Override
    public Qna save(QnADto.Request qnaDto, long userId) {

        return qnARepository.save(qnaDto.toQnaEntity(userId));
    }

    @Override
    public Page<Qna> findByUserId(long userId, Pageable pageable) {
        Page<Qna> qnaPage = qnARepository.findByUserId(userId, pageable);

//        qnaPage.map(m -> new QnADto);
        return qnaPage;
    }

    @Override
    public void deleteQnaByQnAId(long qnaId) {
        qnARepository.deleteById(qnaId);
    }

    @Override
    public Page<Qna> findAll(Pageable pageable) {
//        Page<Qna> qnaPage = qnARepository.findAll(pageable);
//        qnaPage.map(m -> new QnADto());

        return qnARepository.findAll(pageable);
    }
}
