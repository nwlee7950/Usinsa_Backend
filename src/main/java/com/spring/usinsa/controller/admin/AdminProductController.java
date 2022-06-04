package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final ProductDetailService productDetailService;
    private final SubCategoryService subCategoryService;

    @GetMapping
    public String findAll(Model model, @RequestParam(required = false) Long brandId, @RequestParam(required = false) Long categoryId, Pageable pageable){
        model.addAttribute("pages", productService.findByProductRequest(categoryId, brandId, pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        System.out.println(model.getAttribute("pages"));

        return "product/productList";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("product", productDetailService.findById(id));

        return "product/productDetail";
    }

    @GetMapping("/add")
    public String registerProduct(Model model){
        model.addAttribute("categoryList", subCategoryService.findAll());
        model.addAttribute("brandList", brandService.findAll());
        return "product/productSave";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute ProductDto.Request productDto) throws Exception{
            productService.save(productDto);

        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.findById(id));

        return "product/productUpdate";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute ProductDto.UpdateRequest productDto){
        try {
            productService.update(productDto, id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/admin/product";
    }

}
