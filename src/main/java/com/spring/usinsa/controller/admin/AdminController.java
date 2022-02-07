package com.spring.usinsa.controller.admin;

import com.spring.usinsa.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);

        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
