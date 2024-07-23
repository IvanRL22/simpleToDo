package com.ivanrl.simpleToDo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService userDetailsService;

    @GetMapping
    public String user(@AuthenticationPrincipal UserDetails user,
                       Model model) {

        model.addAttribute("userInfo", new UserInfo(user.getUsername()));

        return "user";
    }
}

record UserInfo(String username) {}
