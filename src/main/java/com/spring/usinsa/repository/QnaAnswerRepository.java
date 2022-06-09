package com.spring.usinsa.repository;

import com.spring.usinsa.model.inquiry.QnaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaAnswerRepository extends JpaRepository<QnaAnswer, Long> {
}
