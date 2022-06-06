package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.model.product.ProductSize;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeDto {

    private String size;
    private Float size1;
    private Float size2;
    private Float size3;
    private int total;
    private Long sizeId;

    public ProductSize toProductSizeEntity(Long productDetailId){
        return ProductSize.builder()
                .size(this.size)
                .size1(this.size1)
                .size2(this.size2)
                .size3(this.size3)
                .total(this.total)
                .productDetailId(productDetailId)
                .build();
    }

    public static ProductSizeDto toProductSizeDto(ProductSize productSize) {
        return ProductSizeDto.builder()
                .size(productSize.getSize())
                .size1(productSize.getSize1())
                .size2(productSize.getSize2())
                .size3(productSize.getSize3())
                .total(productSize.getTotal())
                .sizeId(productSize.getId())
                .build();
    }
}
