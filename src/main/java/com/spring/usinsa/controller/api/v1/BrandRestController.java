package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.BrandService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brand")
@CrossOrigin(origins = "http://localhost:3000")
public class BrandRestController {

    private final ApiResponseService apiResponseService;
    private final BrandService brandService;

    @GetMapping
    public SingleResponse<List<BrandDto.Response>> findBrandList(){
        List<BrandDto.Response> brands = brandService.findAll();
        return apiResponseService.getSingleResult(brands);
    }

    @GetMapping("/{id}")
    public SingleResponse<BrandDto.Response> findById(@PathVariable("id") Long id){
        BrandDto.Response brand = brandService.findById(id);

        return apiResponseService.getSingleResult(brand);
    }

    @PostMapping
    public SingleResponse<BrandDto.Response> save(@RequestBody BrandDto.Request brandDto) throws Exception{
        BrandDto.Response brand = brandService.save(brandDto);

        return apiResponseService.getSingleResult(brand);
    }

    @PostMapping("/edit/{id}")
    public SingleResponse<BrandDto.Response> updateBrand(@RequestBody BrandDto.Request brandDto, @PathVariable("id") Long id) throws Exception{
        BrandDto.Response brand = brandService.update(brandDto, id);

        return apiResponseService.getSingleResult(brand);
    }

    @GetMapping("/title")
    public SingleResponse<List<BrandDto.Response>> findByTitleContains(String title){
        List<BrandDto.Response> brands = brandService.findByTitleContains(title);

        return apiResponseService.getSingleResult(brands);
    }

    @GetMapping("/subCategory/{id}")
    public SingleResponse<List<BrandBySubCategoryDto>> findBySubCategory(@PathVariable("id") Long id){
        List<BrandBySubCategoryDto> brands = brandService.findBySubCategory(id);

        return apiResponseService.getSingleResult(brands);
    }

}
