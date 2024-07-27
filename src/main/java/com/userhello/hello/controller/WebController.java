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
    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(User user, Model model) {
        try {
            User savedUser = userService.signUp(user);
            model.addAttribute("name", savedUser.getName());
            return "welcome";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // Redirect back to signup page with error message
        }
    }



    @PostMapping("/login")
    public String login(@RequestParam String uname, Model model, HttpSession session) {
        Optional<User> userOptional = userService.findByUname(uname);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Username not found. Please sign up.");
            return "login"; // Stay on login page and show error
        }
        User user = userOptional.get();
        session.setAttribute("userId", user.getId()); // Storing user ID in session for tracking
        session.setAttribute("username", user.getUname()); // Storing username in session
        model.addAttribute("name", user.getName());
        return "welcome";
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";  // Redirect to log in if no user is logged in
        }
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return "redirect:/login";  // Redirect to log in if user is not found
        }
        model.addAttribute("name", user.get().getName());
        return "welcome";  // Ensure that a 'welcome.html' view exists
    }



    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
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
        return "users";
    }


    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user-edit-form";
    }

    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, Model model) {
        User existingUser = userService.updateUser(updatedUser); // Assumes updateUser handles finding and saving
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
        // This should be handled correctly by your quiz service
        return ResponseEntity.ok("Score submitted successfully. Your score: " + score);
    }

    @Autowired
    UserRepository userRepository;

    public void signUp(User user) {
        Optional<User> existingUser = userRepository.findByUname(user.getUname());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        userRepository.save(user);
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId"); // Remove specific attribute
        session.removeAttribute("username"); // Ensure username is also removed
        return "redirect:/login"; // Redirect to login page
    }
}
