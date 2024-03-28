package com.userhello.hello.controller;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    public String login(@RequestParam String name, @RequestParam String password, Model model, HttpSession session) {
        List<User> users = userRepository.findByName(name);

        for (User user : users) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("authenticated", true); // Set session attribute on successful login
                model.addAttribute("name", user.getName());
                return "welcome";
            }
        }

        return "accessDenied";
    }

    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/"; // Redirect to log in if not authenticated
        }
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    // Ensure this endpoint is also protected
    @GetMapping("/welcome")
    public String welcome(HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/"; // Redirect to log in if not authenticated
        }
        return "welcome";
    }

    @PostMapping("/editUser")
    public String editUser(User user, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/"; // Redirect to log in if not authenticated
        }
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            userRepository.save(updatedUser);
        }
        return "redirect:/users";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/"; // Redirect to log in if not authenticated
        }
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestParam Long id, @RequestParam String newPassword, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/"; // Redirect to log in if not authenticated
        }
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword); // Ideally, you should hash the password
            userRepository.save(user);
        }
        return "redirect:/users";
    }
}
