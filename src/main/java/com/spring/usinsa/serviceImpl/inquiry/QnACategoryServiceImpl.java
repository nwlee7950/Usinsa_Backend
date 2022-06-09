package com.spring.usinsa.serviceImpl.inquiry;

import com.spring.usinsa.dto.inquiry.QnACategoryDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.inquiry.QnaCategory;
import com.spring.usinsa.repository.QnACategoryRepository;
import com.spring.usinsa.service.QnACategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnACategoryServiceImpl implements QnACategoryService {

    private final QnACategoryRepository qnaCategoryRepository;

    @Override
    public QnaCategory findById(Long id) {
        return qnaCategoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.QNA_NOT_FOUND));
    }

    @Override
    public List<QnACategoryDto> findAll() {
        return qnaCategoryRepository.findAll()
                .stream().map(m -> new QnACategoryDto(m)).collect(Collectors.toList());
    }
}
