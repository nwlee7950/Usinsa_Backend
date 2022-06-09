package com.spring.usinsa.serviceImpl.inquiry;

import com.spring.usinsa.dto.inquiry.QnADto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.inquiry.Qna;
import com.spring.usinsa.repository.QnARepository;
import com.spring.usinsa.service.QnACategoryService;
import com.spring.usinsa.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnAServiceImpl implements QnAService {

    private final QnARepository qnARepository;
    private final QnACategoryService qnACategoryService;

    @Override
    public QnADto.Response findById(Long qnaId, User user) {
        Qna qna = qnARepository.findById(qnaId)
                .orElseThrow( () -> new ApiException(ApiErrorCode.QNA_NOT_FOUND));

        // 해당 글쓴이 또는 관리자만 열람 가능
        if(qna.getUserId() != user.getId() && !user.getRoles().stream().anyMatch(role -> role.equals("ROLE_SUPER_ADMIN") ) ){
            throw new ApiException(ApiErrorCode.USER_ROLE_NOT_PERMISSION_READ);
        }

        return QnADto.Response.toQnaResponseDto(qna);
    }

    @Override
    public Qna save(QnADto.Request qnaDto) {

        return qnARepository.save(qnaDto.toQnaEntity(qnACategoryService.findById(qnaDto.getQnaCategoryId())));
    }

    @Override
    public Page<QnADto.Response> findByUserId(Long userId, Pageable pageable) {
        Page<Qna> qnaPage = qnARepository.findByUserId(userId, pageable);

        return qnaPage.map(QnADto.Response::toQnaResponseDto);
    }

    @Override
    public Qna findByIdAsEntity(Long id) {
        return qnARepository.findById(id)
                .orElseThrow( () -> new ApiException(ApiErrorCode.QNA_NOT_FOUND));
    }

    @Override
    public void deleteQnaByQnAId(Long qnaId) {
        qnARepository.deleteById(qnaId);
    }

    @Override
    public Page<QnADto.Response> findAll(Pageable pageable) {
        Page<Qna> qnaPage = qnARepository.findAll(pageable);

        return qnaPage.map(QnADto.Response::toQnaResponseDto);
    }
}
