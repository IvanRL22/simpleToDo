package com.ivanrl.simpleToDo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/signIn")
    public String signIn(Model model) {
        // This is a dirty hack, should find a better way to do it
        model.addAttribute("userSignIn", SignIn.empty());

        return "signIn";
    }

    @PostMapping("/signIn")
    public String signInUser(HttpServletRequest request,
                             SignIn signin,
                             Model model) {
        List<String> errors = validateSignIn(signin);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "signIn";
        }

        userDetailsManager.createUser(
                User.withUsername(signin.username())
                        .password(passwordEncoder.encode(signin.password()))
                        .roles("USER")
                        .build());

        // Authenticate new user
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(signin.username(), signin.password());
        Authentication auth = authenticationManager.authenticate(authToken);

        // Set authentication on request to keep the session
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return "tasks";
    }

    private List<String> validateSignIn(SignIn signin) {
        List<String> errors = new ArrayList<>();

        if (signin.username().isEmpty()) {
            errors.add("Username must be informed");
        } else if (signin.username().length() < 6
                || signin.username().length() > 16) {
            errors.add("Username must be between 6 and 16 characters");
        } else if (userDetailsManager.userExists(signin.username())) {
            errors.add("Username already exists");
        }

        if (signin.password().isEmpty()) {
            errors.add("Password must be informed");
        } else if (signin.password().length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }

        return errors;
    }

}

record SignIn(String username, String password, String fullName) {
    public static SignIn empty() {
        return new SignIn("", "", "");
    }
}
