package com.spring.usinsa.model.product;

import com.spring.usinsa.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewedProduct{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private Long userId;

    private long createdAt;
    private long updatedAt;
}
