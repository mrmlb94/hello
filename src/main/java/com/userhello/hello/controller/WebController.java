package com.userhello.hello.controller;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public WebController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public String signUp(User user, HttpSession session, Model model) {
        User savedUser = userService.signUp(user);
        session.setAttribute("authenticated", true);
        session.setAttribute("userId", savedUser.getId());
        model.addAttribute("name", savedUser.getName());
        return "welcome";
    }

    @PostMapping("/login")
    public String login(@RequestParam String uname, @RequestParam String password, HttpSession session, Model model) {
        List<User> users = userService.findByName(uname);
        if (users.isEmpty()) {
            return "redirect:/signup";
        }
        for (User user : users) {
            if (userService.checkPassword(password, user.getPassword())) {
                session.setAttribute("authenticated", true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("role", user.getRole());
                model.addAttribute("name", user.getName());
                return "welcome";
            }
        }
        return "accessDenied";
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Assuming 'login' is the name of your login template
    }


    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/updateCredentials")
    public String updateCredentials(@RequestParam String newName,
                                    @RequestParam String newPassword,
                                    HttpSession session, Model model) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        Long userId = (Long) session.getAttribute("userId");
        userService.updateCredentials(userId, newName, newPassword);
        return "redirect:/welcome";
    }


    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.canEditUser(id, session)) {
            model.addAttribute("error", "You are not able to change these credentials. You can only update your own.");
            model.addAttribute("username", session.getAttribute("username"));
            return "error";
        }
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user-edit-form";
    }



    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user-edit-form";
        }

        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // Update only the fields that have been changed in the form
        existingUser.setName(updatedUser.getName());
        existingUser.setFamilyName(updatedUser.getFamilyName());
        existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
            // Only encode and set new password if it's not blank
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encodedPassword);
        }
        existingUser.setBirthdate(updatedUser.getBirthdate());
        existingUser.setBirthPlace(updatedUser.getBirthPlace());
        existingUser.setCurrentCountry(updatedUser.getCurrentCountry());
        existingUser.setCurrentCity(updatedUser.getCurrentCity());
        existingUser.setSchoolName(updatedUser.getSchoolName());
        existingUser.setGpa(updatedUser.getGpa());
        existingUser.setPhone(updatedUser.getPhone());

        userRepository.save(existingUser);
        return "redirect:/users";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!userService.canDeleteUser(id, session)) {
            model.addAttribute("error", "You can only delete your own account unless you're an admin.");
            return "redirect:/error";
        }
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestParam Long id,
                                     @RequestParam String newPassword,
                                     HttpSession session, Model model) {
        if (!userService.isUserAuthenticated(id, session)) {
            model.addAttribute("username", session.getAttribute("username"));
            return "error";
        }
        userService.changePassword(id, newPassword);
        return "redirect:/users";
    }
}
