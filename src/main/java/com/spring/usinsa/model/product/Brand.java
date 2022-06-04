package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;     // 브랜드 명
    private String enTitle;   // 브랜드 영어 이름
    private String image;     // 대표 이미지
    private String info;      // 간단한 설명 글
}
