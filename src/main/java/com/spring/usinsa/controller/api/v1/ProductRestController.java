package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.ProductDetailService;
import com.spring.usinsa.service.ProductService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ApiResponseService apiResponseService;
    private final ProductService productService;
    private final ProductDetailService productDetailService;

    @GetMapping
    public SingleResponse<Page<ProductDto.SimpleResponse>> findProduct(Pageable pageable){
        Page<ProductDto.SimpleResponse> products = productService.findAllAsSimpleDto(pageable);

        return apiResponseService.getSingleResult(products);
    }

    @GetMapping("/{id}")
    public SingleResponse<ProductDetailDto.Response> findById(@PathVariable("id") Long id){
        ProductDetailDto.Response product = productDetailService.findById(id);

        return apiResponseService.getSingleResult(product);
    }

    @GetMapping("/category/{id}")
    public SingleResponse<Page<ProductDto.Response>> findByCategoryIdAndBrandId(@PathVariable("id") Long id,
                                                              @RequestParam(required = false) Long brandId,
                                                              Pageable pageable){

        Page<ProductDto.Response> productPage = productService.findByProductRequest(id, brandId, pageable);

        return apiResponseService.getSingleResult(productPage);
    }

    @GetMapping("/brand/{id}")
    public SingleResponse<Page<ProductDto.Response>> findByBrandIdAndCategoryId(@PathVariable("id") Long id,
                                                                                @RequestParam(required = false) Long subCategoryId,
                                                                                Pageable pageable){

        Page<ProductDto.Response> productPage = productService.findByProductRequest(subCategoryId, id, pageable);

        return apiResponseService.getSingleResult(productPage);
    }

    @PostMapping
    public SingleResponse<Product> save(@ModelAttribute ProductDto.Request productDto) throws Exception{
        Product product = productService.save(productDto);

        return apiResponseService.getSingleResult(product);
    }

    @PutMapping
    public SingleResponse<ProductDto.Response> updateById(
            @ModelAttribute ProductDto.UpdateRequest productDto, Long id) throws Exception{

        ProductDto.Response product = productService.update(productDto, id);

        return apiResponseService.getSingleResult(product);
    }

    @DeleteMapping("/{id}")
    public SingleResponse<String> deleteById(@PathVariable Long id){
        productService.deleteById(id);

        return apiResponseService.getSingleResult("success");
    }

}
