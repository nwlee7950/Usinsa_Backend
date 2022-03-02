package com.spring.usinsa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/blog/content")
public class AdminBlogContentController {

    private final BlogCategoryService blogCategoryService;
    private final BlogContentService blogContentService;

    @GetMapping
    public String findAllByBlogCategory(Model model, Pageable pageable,
                                       @RequestParam(value="blogCategoryId", required = false) Long blogCategoryId) {

        if(blogCategoryId == null)
            model.addAttribute("pages", blogContentService.findAll(pageable));
        else
            model.addAttribute("pages", blogContentService.findAllByBlogCategory_Id(blogCategoryId, pageable));

        model.addAttribute("blogCategoryList", blogCategoryService.findAll());
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("blogCategoryId", blogCategoryId);

        return "blogContent/blogContentList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        if(id == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }
        model.addAttribute("blogContent", blogContentService.findById(id));

        return "blogContent/blogContentDetail";
    }

    @PostMapping
    public String save(@ModelAttribute AdminBlogContentSaveRequestDto blogContentSaveRequestDto) throws Exception {
        if(blogContentSaveRequestDto.getBlogCategoryId() == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        BlogContent savedBlogContent = blogContentService.save(blogContentSaveRequestDto);

        return "redirect:/admin/blog/content/" + savedBlogContent.getId();
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AdminBlogContentUpdateRequestDto blogContentUpdateRequestDto) throws Exception {

        if(blogContentUpdateRequestDto.getBlogCategoryId() == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        BlogContent updatedBlogContent
                = blogContentService.update(id, blogContentUpdateRequestDto);

        return "redirect:/admin/blog/content/" + updatedBlogContent.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        blogContentService.deleteById(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("blogCategoryList", blogCategoryService.findAll());
        model.addAttribute("blogContent", blogContentService.findById(id));
        return "blogContent/blogContentUpdate";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("blogCategoryList", blogCategoryService.findAll());
        return "blogContent/blogContentSave";
    }
}
