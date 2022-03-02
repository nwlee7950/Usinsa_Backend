package com.spring.usinsa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/guide/faq")
public class AdminFaqController {

    private final FaqService faqService;
    private final FaqCategoryService faqCategoryService;

    @GetMapping
    public String findAllByFaqCategory(Model model, Pageable pageable,
                                       @RequestParam(value="faqCategoryId", required = false) Long faqCategoryId) {

        if(faqCategoryId == null)
            model.addAttribute("pages", faqService.findAll(pageable));
        else
            model.addAttribute("pages", faqService.findAllByFaqCategory_Id(faqCategoryId, pageable));

        model.addAttribute("faqCategoryList", faqCategoryService.findAll());
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("faqCategoryId", faqCategoryId);

        return "faq/faqList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        if(id == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }
        model.addAttribute("faq", faqService.findById(id));

        return "faq/faqDetail";
    }

    @PostMapping
    public String save(@ModelAttribute AdminFaqSaveRequestDto faqSaveRequestDto){
        if(faqSaveRequestDto.getFaqCategoryId() == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        FaqCategory faqCategory = faqCategoryService.findById(faqSaveRequestDto.getFaqCategoryId());
        Faq savedFaq = faqService.save(faqSaveRequestDto.toFaqEntity(faqCategory));

        return "redirect:/admin/guide/faq/" + savedFaq.getId();
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AdminFaqUpdateRequestDto faqUpdateRequestDto){

        if(faqUpdateRequestDto.getFaqCategoryId() == null) {
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        FaqCategory faqCategory = faqCategoryService.findById(faqUpdateRequestDto.getFaqCategoryId());
        Faq updatedFaq = faqService.update(id, faqUpdateRequestDto.toFaqEntity(faqCategory));

        return "redirect:/admin/guide/faq/" + updatedFaq.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        faqService.deleteById(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("faqCategoryList", faqCategoryService.findAll());
        model.addAttribute("faq", faqService.findById(id));
        return "faq/faqUpdate";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("faqCategoryList", faqCategoryService.findAll());
        return "faq/faqSave";
    }
}
