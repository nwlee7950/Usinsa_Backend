package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubCategoryByBrandIdDto {

    private Long id;
    private String title;
    private Long productCount;

    public SubCategoryByBrandIdDto(Long id, String title, Long productCount){
        this.id = id;
        this.title = title;
        this.productCount = productCount;
    }
}
