package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Ranking, Long> {
    Page<Ranking> findByTermTypeOrderByRankingAsc(String termType, Pageable pageable);
    void deleteByTermType(String termType);
}
