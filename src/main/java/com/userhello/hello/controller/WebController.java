package com.userhello.hello.controller;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {
    private static final String LOGIN_VIEW = "login";
    private static final String SIGNUP_VIEW = "signup";
    private static final String WELCOME_VIEW = "welcome";
    private static final String USERS_VIEW = "users";
    private static final String USER_EDIT_FORM_VIEW = "user-edit-form";
    private static final String QUIZ_VIEW = "quiz";
    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USERNAME_SESSION_ATTRIBUTE = "username";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    private final UserService userService;
    UserRepository userRepository; // Assuming you need UserRepository as well

    @Autowired
    public WebController(UserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return LOGIN_VIEW;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return SIGNUP_VIEW;
    }

    @PostMapping("/signup")
    public String signUp(User user, Model model) {
        try {
            User savedUser = userService.signUp(user);
            model.addAttribute("name", savedUser.getName());
            return WELCOME_VIEW;
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return SIGNUP_VIEW;
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String uname, Model model, HttpSession session) {
        Optional<User> userOptional = userService.findByUname(uname);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Username not found. Please sign up.");
            return LOGIN_VIEW;
        }
        User user = userOptional.get();
        session.setAttribute(USER_ID_SESSION_ATTRIBUTE, user.getId());
        session.setAttribute(USERNAME_SESSION_ATTRIBUTE, user.getUname());
        model.addAttribute("name", user.getName());
        return WELCOME_VIEW;
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
        if (userId == null) {
            return REDIRECT_LOGIN;
        }
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return REDIRECT_LOGIN;
        }
        model.addAttribute("name", user.get().getName());
        return WELCOME_VIEW;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return LOGIN_VIEW;
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(name = "sort", required = false) String sort, Model model) {
        List<User> users;
        if ("name".equals(sort)) {
            users = userService.findAllUsersSortedByName();
        } else {
            users = userService.findAllUsers();
        }
        model.addAttribute("users", users);
        return USERS_VIEW;
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return USER_EDIT_FORM_VIEW;
    }

    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, Model model) {
        userService.updateUser(updatedUser); // Assumes updateUser handles finding and saving
        return "redirect:/users";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return QUIZ_VIEW;
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<String> submitQuiz(@RequestParam("score") int score) {
        // This should be handled correctly by your quiz service
        return ResponseEntity.ok("Score submitted successfully. Your score: " + score);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_ID_SESSION_ATTRIBUTE);
        session.removeAttribute(USERNAME_SESSION_ATTRIBUTE);
        return REDIRECT_LOGIN;
    }

    public void signUp(User user) {
        Optional<User> existingUser = userRepository.findByUname(user.getUname());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        userRepository.save(user);
    }
}
