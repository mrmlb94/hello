package com.userhello.hello.controller;

import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final UserRepository userRepository;

    public WebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, Model model) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        model.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

}
