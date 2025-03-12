package com.userhello.hello.controller;

import com.userhello.hello.service.UserService;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.exception.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class WebController {
    private static final String LOGIN_VIEW = "login";
    private static final String WELCOME_VIEW = "welcome";
    private static final String USER_ID_ATTR = "userId";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String SIGNUP_VIEW = "signup";
    private static final String ERROR_ATTR = "error";


    private final UserService userService;
    private final UserRepository userRepository;

    public WebController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        logger.info("🚀 Loading login page...");
        model.addAttribute("user", new User());
        return LOGIN_VIEW;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return REDIRECT_LOGIN; 
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return SIGNUP_VIEW;
    }

    @PostMapping("/signup")
    public String signUp(User user, Model model) {
        try {
            Optional<User> existingUser = userRepository.findByUname(user.getUname());
            if (existingUser.isPresent()) {
                throw new UsernameAlreadyExistsException("Username already exists");
            }
            User savedUser = userRepository.save(user);
            model.addAttribute("name", savedUser.getName());
            return WELCOME_VIEW;
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute(ERROR_ATTR, e.getMessage());
            return "signup";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String uname, Model model, HttpSession session) {
        Optional<User> userOptional = userService.findByUname(uname);
        if (userOptional.isEmpty()) {
            model.addAttribute(ERROR_ATTR, "Username not found. Please sign up.");
            return LOGIN_VIEW;
        }
        User user = userOptional.get();
        session.setAttribute(USER_ID_ATTR, user.getId());
        session.setAttribute("username", user.getUname());
        model.addAttribute("name", user.getName());
        return WELCOME_VIEW;
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute(USER_ID_ATTR);
        if (userId == null || userService.findById(userId).isEmpty()) {
            model.addAttribute(ERROR_ATTR, "Please log in first.");
            return LOGIN_VIEW;
        }
        model.addAttribute("name", userService.findById(userId).get().getName());
        return WELCOME_VIEW;
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(name = "sort", required = false) String sort, Model model) {
        List<User> users = "name".equals(sort) ? userService.findAllUsersSortedByName() : userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user-edit-form";
    }

    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        userService.updateUser(updatedUser);
        return "redirect:/users";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz";
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<String> submitQuiz(@RequestParam("score") int score) {
        return ResponseEntity.ok("Score submitted successfully. Your score: " + score);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_ID_ATTR);
        session.removeAttribute("username");
        return REDIRECT_LOGIN;
    }
}
