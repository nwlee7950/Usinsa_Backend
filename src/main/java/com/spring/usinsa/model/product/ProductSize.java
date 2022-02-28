package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSize {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int total;

    private int size;
    private String color;
    private Float length;

    // 상의 기준
    private Float sholderSize;
    private Float bustSize;
    private Float sleeveSize;

}
