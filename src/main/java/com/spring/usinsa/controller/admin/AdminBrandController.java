package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/brand")
public class AdminBrandController {

    @Autowired
    private final BrandService brandService;

    @GetMapping
    public String findAll(Model model, Pageable pageable){
        model.addAttribute("pages", brandService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "brand/brandList";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("brand",brandService.findById(id));

        return "brand/brandDetail";
    }

    @GetMapping("/add")
    public String registerBrand(){
        return "brand/brandSave";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute BrandDto.Request brandDto) throws Exception{
        BrandDto.Response brand = brandService.save(brandDto);

        return "redirect:/admin/brand/" + brand.getBrandId();
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute BrandDto.Request brandDto,
                         @PathVariable("id") Long id) throws Exception{
        if(brandDto.getTitle() == null || brandDto.getInfo() == null || brandDto.getEnTitle() == null || brandDto.getImage() == null){
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        brandService.update(brandDto, id);
        return "redirect:/admin/brand/" + id;
    }

    @GetMapping("/edit/{id}")
    public String updateBrand(Model model, @PathVariable Long id){
        model.addAttribute("brand",brandService.findById(id));

        return "brand/brandUpdate";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        brandService.deleteById(id);

        return "redirect:/admin/brand";
    }

}
