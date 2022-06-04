package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentImage;

    @ManyToOne
    private Product product;

    @OneToMany
    @JoinColumn(name = "productDetailId")
    private List<ProductImage> productImages;

    @OneToMany
    @JoinColumn(name = "productDetailId")
    private List<ProductSize> productSize;
}
