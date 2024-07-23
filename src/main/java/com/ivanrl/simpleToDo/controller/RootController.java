package com.ivanrl.simpleToDo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RootController {


    private static NavBarLink[] createNavBarLinks(UserDetails userDetails) {
        // TODO This way of rendering navlinks should be done in a better way
        var isAuth = userDetails != null && userDetails.getUsername() != null;
        return new NavBarLink[] {
                new NavBarLink("Home", "/home"),
                new NavBarLink("Tasks", "/tasks", isAuth),
                new NavBarLink("Profile", "/user", isAuth)

        };
    }


    @GetMapping(value = "/")
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("navLinks", createNavBarLinks(userDetails));
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }

        return "index";
    }

    @GetMapping(value = "/home", headers = "HX-Request")
    public String home() {
        return "home";
    }

    @GetMapping("/error")
    public void error() {
    }

}

record NewTask(String name) {}
record NavBarLink(String text, String link, boolean isVisible) {

    public NavBarLink(String text, String link) {
        this(text, link, true);
    }

}

