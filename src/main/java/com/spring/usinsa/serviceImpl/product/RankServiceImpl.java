package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.RankDto;
import com.spring.usinsa.dto.product.RankRequestDto;
import com.spring.usinsa.repository.RankRepository;
import com.spring.usinsa.repository.customRepository.RankingRepositoryCustom;
import com.spring.usinsa.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;
    private final RankingRepositoryCustom rankingRepositoryCustom;

    @Override
    public Page<RankRequestDto> findAllByDaily(Pageable pageable) {

        return rankRepository.findByTermTypeOrderByRankingAsc("daily", pageable)
                .map(RankRequestDto::toRankRequestDto);
    }

    @Override
    public Page<RankRequestDto> findAllByTime(Pageable pageable) {
        return rankRepository.findByTermTypeOrderByRankingAsc("time", pageable)
                .map(RankRequestDto::toRankRequestDto);
    }

    /**
     * 랭킹 삭제 후 다시 쓰기
     * @param rankDtoList
     * @param termType
     */
    @Override
    @Transactional
    public void save(List<RankDto> rankDtoList, String termType) {
        rankingRepositoryCustom.bulkDeleteByTermType(termType);

        // auto Increment 전략 + jpa를 이용 시 bulk insert는 지원 안함
        for ( RankDto rankDto : rankDtoList ) {
            rankRepository.save(rankDto.toRankingEntity(termType));
        }
    }
}
