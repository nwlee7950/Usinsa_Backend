package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.RankDto;
import com.spring.usinsa.dto.product.RankRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RankService {
    Page<RankRequestDto> findAllByDaily(Pageable pageable);
    Page<RankRequestDto> findAllByTime(Pageable pageable);
    void save(List<RankDto> rankDtoList, String termType);
}
