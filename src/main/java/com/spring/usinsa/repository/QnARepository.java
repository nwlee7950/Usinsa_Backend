package com.spring.usinsa.repository;

import com.spring.usinsa.model.inquiry.Qna;
import com.spring.usinsa.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QnARepository extends JpaRepository<Qna, Long> {

    Optional<Qna> findById(long id);
    Page<Qna> findByUserId(long userId, Pageable pageable);
    Page<Qna> findAll(Pageable pageable); //  관리자 용
    long deleteById(long id);

}
