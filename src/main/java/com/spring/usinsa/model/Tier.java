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
    @Column
    private String title;

    @Column
    private Long startScore;

    @Column
    private Long endScore;
}
