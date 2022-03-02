package com.spring.usinsa.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "productDetailId")
    private List<ProductImage> productImages;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "productDetailId")
    private List<ProductSize> productSize;
}
