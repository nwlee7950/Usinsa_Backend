package com.spring.usinsa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/guide/faq-category")
public class AdminFaqCategoryController {

    private final FaqCategoryService faqCategoryService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {

        model.addAttribute("pages", faqCategoryService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "faqCategory/faqCategoryList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        if(id == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }
        model.addAttribute("faqCategory", faqCategoryService.findById(id));

        return "faqCategory/faqCategoryDetail";
    }

    @PostMapping
    public String save(@ModelAttribute AdminFaqCategorySaveRequestDto faqCategorySaveRequestDto){
        FaqCategory savedFaqCategory = faqCategoryService.save(faqCategorySaveRequestDto.toFaqCategoryEntity());
        return "redirect:/admin/guide/faq-category/" + savedFaqCategory.getId();
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AdminFaqCategoryUpdateRequestDto faqCategoryUpdateRequestDto){
        FaqCategory updatedFaqCategory = faqCategoryService.update(id, faqCategoryUpdateRequestDto.toFaqCategoryEntity());
        return "redirect:/admin/guide/faq-category/" + updatedFaqCategory.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        faqCategoryService.deleteById(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("faqCategory", faqCategoryService.findById(id));
        return "faqCategory/faqCategoryUpdate";
    }

    @GetMapping("/add")
    public String add(){
        return "faqCategory/faqCategorySave";
    }
}
