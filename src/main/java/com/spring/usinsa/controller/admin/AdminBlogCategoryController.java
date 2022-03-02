package com.spring.usinsa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/blog/category")
public class AdminBlogCategoryController {

    private final BlogCategoryService blogCategoryService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {

        model.addAttribute("pages", blogCategoryService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "blogCategory/blogCategoryList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        if(id == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }
        model.addAttribute("blogCategory", blogCategoryService.findById(id));

        return "blogCategory/blogCategoryDetail";
    }

    @PostMapping
    public String save(@ModelAttribute AdminBlogCategorySaveRequestDto blogCategorySaveRequestDto){
        BlogCategory savedBlogCategory = blogCategoryService.save(blogCategorySaveRequestDto.toBlogCategoryEntity());
        return "redirect:/admin/blog/category/" + savedBlogCategory.getId();
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AdminBlogCategoryUpdateRequestDto blogCategoryUpdateRequestDto){
        BlogCategory updatedBlogCategory = blogCategoryService.update(id, blogCategoryUpdateRequestDto.toBlogCategoryEntity());
        return "redirect:/admin/blog/category/" + updatedBlogCategory.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        blogCategoryService.deleteById(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("blogCategory", blogCategoryService.findById(id));
        return "blogCategory/blogCategoryUpdate";
    }

    @GetMapping("/add")
    public String add(){
        return "blogCategory/blogCategorySave";
    }
}
