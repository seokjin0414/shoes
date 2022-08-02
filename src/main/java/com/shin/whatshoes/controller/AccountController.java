package com.shin.whatshoes.controller;

import com.shin.whatshoes.model.User;
import com.shin.whatshoes.repository.UserRepository;
import com.shin.whatshoes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/signin")
    public String showSignin() {
        return "account/signin";
    }

    @GetMapping("/register")
    public String showRegister(User user) {
//        if (userId == null) {
//            model.addAttribute("user", new User());
//        } else {
//            User user = userRepository.findById(userId).orElse(null);
//            model.addAttribute("user", user);
//        }
        return "account/register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("userdata", user);
            return "account/register";
        }

        userService.save(user);
        return "redirect:/";
    }

}
