package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

//    @GetMapping
//    public String findAllByBlogCategory(Model model, Pageable pageable,
//                                       @RequestParam(value="blogCategoryId", required = false) Long blogCategoryId) {
//
//        if(blogCategoryId == null)
//            model.addAttribute("pages", productService.findAll(pageable));
//        else
//            model.addAttribute("pages", productService.findAllByBlogCategory_Id(blogCategoryId, pageable));
//
//        model.addAttribute("blogCategoryList", productService.findAll());
//        model.addAttribute("maxPage", 5);
//        model.addAttribute("currentPage", pageable.getPageNumber());
//        model.addAttribute("blogCategoryId", blogCategoryId);
//
//        return "blogContent/blogContentList";
//    }

//    @GetMapping("/{id}")
//    public String findById(@PathVariable("id") Long id, Model model){
//        if(id == null) {
//            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
//        }
//        model.addAttribute("blogContent", productService.findById(id));
//
//        return "blogContent/blogContentDetail";
//    }

    // 상품 추가 ( 대표이미지 설정 )
    @PostMapping
    public String save(@ModelAttribute ProductDto.Request productDto) throws Exception {
        if(productDto.getBrandId() == null) {
            throw new ApiException(ApiErrorCode.BRAND_NOT_FOUND);
        }

        Product savedProduct = productService.save(productDto);

        return "redirect:/admin/product/" + savedProduct.getId();
    }

    // 상품 추가 ( 대표이미지 수정 )
    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute ProductDto.UpdateRequest productDto) throws Exception {

        if(productDto.getBrand() == null) {
            throw new ApiException(ApiErrorCode.BRAND_NOT_FOUND);
        }

        Product updatedProduct
                = productService.updateProduct(id, productDto);

        return "redirect:/admin/product/" + updatedProduct.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
//        model.addAttribute("blogCategoryList", productService.findAll());
//        model.addAttribute("blogContent", productService.findById(id));
        return "blogContent/blogContentUpdate";
    }

    @GetMapping("/add")
    public String add(Model model){
//        model.addAttribute("blogCategoryList", productService.findAll());
        return "blogContent/blogContentSave";
    }
}
