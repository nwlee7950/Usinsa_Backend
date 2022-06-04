package com.spring.usinsa.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long productDetailId;

    private int total;

    private String size;

    private Float size1;
    private Float size2;
    private Float size3;
}
