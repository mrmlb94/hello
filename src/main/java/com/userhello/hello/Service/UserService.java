package com.userhello.hello.Service;

import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User updateCredentials(Long userId, String newName, String newPassword) {
        User user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(newName);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean canDeleteUser(Long userId, HttpSession session) {
        Long sessionUserId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        return userId.equals(sessionUserId) || "ADMIN".equals(role);
    }

    public boolean canEditUser(Long userId, HttpSession session) {
        Long sessionUserId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        return userId.equals(sessionUserId) || "ADMIN".equals(role);
    }

    public boolean isUserAuthenticated(Long userId, HttpSession session) {
        return userId.equals(session.getAttribute("userId"));
    }

    public void changePassword(Long id, String newPassword) {
        User user = findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
