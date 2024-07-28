package com.userhello.hello.controller;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WebControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private WebController webController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webController = new WebController(userService);
        webController.userRepository = userRepository; // Manually inject the mock
    }

    @Test
    public void testShowLogin() {
        String viewName = webController.showLogin(model);
        assertEquals("login", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testShowSignupForm() {
        String viewName = webController.showSignupForm(model);
        assertEquals("signup", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testSignUp_Success() {
        User user = new User();
        user.setName("John");
        when(userService.signUp(user)).thenReturn(user);

        String viewName = webController.signUp(user, model);
        assertEquals("welcome", viewName);
        verify(model).addAttribute("name", "John");
    }

    @Test
    public void testSignUp_Failure() {
        User user = new User();
        when(userService.signUp(user)).thenThrow(new RuntimeException("Error"));

        String viewName = webController.signUp(user, model);
        assertEquals("signup", viewName);
        verify(model).addAttribute("error", "Error");
    }

    @Test
    public void testLogin_Success() {
        User user = new User();
        user.setId(1L);
        user.setUname("john");
        user.setName("John");

        when(userService.findByUname("john")).thenReturn(Optional.of(user));

        String viewName = webController.login("john", model, session);
        assertEquals("welcome", viewName);
        verify(session).setAttribute("userId", 1L);
        verify(session).setAttribute("username", "john");
        verify(model).addAttribute("name", "John");
    }

    @Test
    public void testLogin_Failure() {
        when(userService.findByUname("john")).thenReturn(Optional.empty());

        String viewName = webController.login("john", model, session);
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Username not found. Please sign up.");
    }

    @Test
    public void testWelcomePage_LoggedIn() {
        User user = new User();
        user.setName("John");

        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        String viewName = webController.welcomePage(model, session);
        assertEquals("welcome", viewName);
        verify(model).addAttribute("name", "John");
    }

    @Test
    public void testWelcomePage_NotLoggedIn() {
        when(session.getAttribute("userId")).thenReturn(null);

        String viewName = webController.welcomePage(model, session);
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testWelcomePage_UserNotFound() {
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(Optional.empty());

        String viewName = webController.welcomePage(model, session);
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowLoginPage() {
        String viewName = webController.showLoginPage();
        assertEquals("login", viewName);
    }

    @Test
    public void testListUsers_NoSort() {
        List<User> users = new ArrayList<>();
        when(userService.findAllUsers()).thenReturn(users);

        String viewName = webController.listUsers(null, model);
        assertEquals("users", viewName);
        verify(model).addAttribute("users", users);
    }

    @Test
    public void testListUsers_SortByName() {
        List<User> users = new ArrayList<>();
        when(userService.findAllUsersSortedByName()).thenReturn(users);

        String viewName = webController.listUsers("name", model);
        assertEquals("users", viewName);
        verify(model).addAttribute("users", users);
    }

    @Test
    public void testEditUserForm() {
        User user = new User();
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        String viewName = webController.editUserForm(1L, model);
        assertEquals("user-edit-form", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void testEditUserForm_UserNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        try {
            webController.editUserForm(1L, model);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid user Id:1", e.getMessage());
        }
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        when(userService.updateUser(user)).thenReturn(user);

        String viewName = webController.updateUser(1L, user, model);
        assertEquals("redirect:/users", viewName);
    }

    @Test
    public void testDeleteUser() {
        String viewName = webController.deleteUser(1L);
        assertEquals("redirect:/users", viewName);
        verify(userService).deleteUser(1L);
    }

    @Test
    public void testQuizPage() {
        String viewName = webController.quizPage();
        assertEquals("quiz", viewName);
    }

    @Test
    public void testSubmitQuiz() {
        ResponseEntity<String> response = webController.submitQuiz(100);
        assertEquals("Score submitted successfully. Your score: 100", response.getBody());
    }

    @Test
    public void testLogout() {
        String viewName = webController.logout(session);
        assertEquals("redirect:/login", viewName);
        verify(session).removeAttribute("userId");
        verify(session).removeAttribute("username");
    }

    @Test
    public void testDirectSignUp_Success() {
        User user = new User();
        user.setUname("john");
        when(userRepository.findByUname("john")).thenReturn(Optional.empty());

        webController.signUp(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testDirectSignUp_UsernameExists() {
        User user = new User();
        user.setUname("john");
        when(userRepository.findByUname("john")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            webController.signUp(user);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}