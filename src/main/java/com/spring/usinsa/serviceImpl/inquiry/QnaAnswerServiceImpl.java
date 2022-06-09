package com.spring.usinsa.serviceImpl.inquiry;

import com.spring.usinsa.dto.inquiry.QnAAnswerDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.inquiry.QnaAnswer;
import com.spring.usinsa.repository.QnaAnswerRepository;
import com.spring.usinsa.service.QnAService;
import com.spring.usinsa.service.QnaAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaAnswerServiceImpl implements QnaAnswerService {

    private final QnaAnswerRepository qnaAnswerRepository;
    private final QnAService qnAService;
    @Override
    public void save(QnAAnswerDto qnAAnswerDto) {
        qnaAnswerRepository.save( qnAAnswerDto.toQnaAnswerEntity(qnAService.findByIdAsEntity( qnAAnswerDto.getQnaId() )) );
    }

    @Override
    public QnAAnswerDto findById(Long id) {
        QnaAnswer qnaAnswer = qnaAnswerRepository.findById(id)
                .orElseThrow( () -> new ApiException(ApiErrorCode.QNA_ANSWER_NOT_FOUND));

        return QnAAnswerDto.toQnaAnswerDto(qnaAnswer);
    }

    @Override
    public Boolean existsById(Long id) {
        return qnaAnswerRepository.existsById(id);
    }
}
