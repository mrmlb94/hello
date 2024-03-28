package com.userhello.hello.controller;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;
    public WebController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        session.setAttribute("authenticated", true);
        session.setAttribute("userId", user.getId());
        model.addAttribute("name", user.getName());
        return "welcome";
    }
    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, HttpSession session, Model model) {
        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            return "redirect:/signup";
        }
        for (User user : users) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("authenticated", true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("role", user.getRole()); // Storing the user's role in the session
                model.addAttribute("name", user.getName());
                return "welcome";
            }
        }
        return "accessDenied";
    }


    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        return "welcome";
    }

    @PostMapping("/updateCredentials")
    public String updateCredentials(@RequestParam String newName,
                                    @RequestParam String newPassword,
                                    HttpSession session, Model model) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("userId");
        if (!userId.equals(session.getAttribute("userId"))) {
            model.addAttribute("username", session.getAttribute("username"));
            return "error";
        }

        Optional<User> userOptional = userRepository.findById(userId); // userOptional should be declared here
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(newName);
            // Encode the new password before saving it
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "redirect:/welcome";
        }
        model.addAttribute("username", session.getAttribute("username"));
        return "error";
    }


    @PostMapping("/editUser")
    public String editUser(User user, HttpSession session, Model model) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        if (!session.getAttribute("userId").equals(user.getId())) {
            model.addAttribute("username", session.getAttribute("username"));
            return "error";
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
    public String deleteUser(@PathVariable("id") Long id, HttpSession session, Model model) {
        Long sessionUserId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (!id.equals(sessionUserId) && !"ADMIN".equals(role)) {
            model.addAttribute("error", "You can only delete your own account.");
            return "redirect:/error"; // redirect to an error page or display the message in the UI
        }
        userRepository.deleteById(id);
        return "redirect:/users";
    }



    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestParam Long id,
                                     @RequestParam String newPassword,
                                     HttpSession session, Model model) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        if (!session.getAttribute("userId").equals(id)) {
            model.addAttribute("username", session.getAttribute("username"));
            return "error";
        }
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Ensure the password is encoded before saving
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        return "redirect:/users";
    }
}
