package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.product.CategoryDto;
import com.spring.usinsa.dto.product.SubCategoryDto;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.service.CategoryService;
import com.spring.usinsa.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String findAll(Model model, Pageable pageable){
        model.addAttribute("pages", categoryService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "category/categoryList";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("category", categoryService.findById(id));

        return "category/categoryDetail";
    }

    @GetMapping("/add")
    public String registerCategory(){
        return "category/categorySave";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute CategoryDto.Request categoryDto){
        categoryService.save(categoryDto);

        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("category", categoryService.findById(id));

        return "category/categoryUpdate";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute CategoryDto.UpdateRequest categoryDto){
        categoryService.update(categoryDto, id);

        return "redirect:/admin/category";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        categoryService.deleteById(id);

        return "redirect:/admin/category";
    }
}
