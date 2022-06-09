package com.spring.usinsa.repository;

import com.spring.usinsa.model.inquiry.QnaCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnACategoryRepository extends JpaRepository<QnaCategory, Long> {

}
