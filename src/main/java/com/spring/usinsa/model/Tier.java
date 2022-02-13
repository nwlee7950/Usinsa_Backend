package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 티어명 (ex - BRONZE, SILVER, GOLD ... )
    private String title;
    private Long startScore;
    private Long endScore;
}
