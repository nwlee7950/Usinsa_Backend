package com.spring.usinsa.controller.admin;

import com.spring.usinsa.dto.inquiry.QnAAnswerDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.service.QnAService;
import com.spring.usinsa.service.QnaAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/qna")
public class AdminQnAController {

    private final QnAService qnAService;
    private final QnaAnswerService qnaAnswerService;

    @GetMapping
    public String findAll(Model model, Pageable pageable){

        model.addAttribute("qnaList" , qnAService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "qna/qnaList";
    }

    @GetMapping("{id}")
    public String findById (Model model , @PathVariable Long id, @AuthenticationPrincipal User user){

        model.addAttribute("counsel", qnAService.findById(id, user));

        return "qna/qnaDetail";
    }

    @PostMapping("/answer/{id}")
    public String save (@PathVariable Long id, @AuthenticationPrincipal User user, @ModelAttribute QnAAnswerDto qnAAnswerDto){
        qnAAnswerDto.setQnaId(id);
        qnaAnswerService.save(qnAAnswerDto);

        return "redirect:/admin/qna";
    }

}
