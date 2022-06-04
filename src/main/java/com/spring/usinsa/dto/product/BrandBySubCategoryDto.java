package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class BrandBySubCategoryDto {
    private Long brandId;
    private Long productTotal;
    private String brandTitle;
//        private Boolean brandLike;
//        private Boolean discount;

    public BrandBySubCategoryDto(Long brandId, String brandTitle, Long productTotal){
        this.brandId = brandId;
        this.productTotal = productTotal;
        this.brandTitle = brandTitle;
    }
}