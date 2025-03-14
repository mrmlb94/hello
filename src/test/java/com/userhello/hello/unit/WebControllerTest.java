package com.userhello.hello.unit;

import com.userhello.hello.controller.WebController;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WebControllerTest {

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webController = new WebController(userService, userRepository);
    }

    
    @Test
    void testRedirectToLogin() {
        String viewName = webController.redirectToLogin();
        assertEquals("redirect:/login", viewName);
    }

    
    @Test
    void testShowLoginPage() {
        String viewName = webController.showLoginPage(model);
        assertEquals("login", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }


    @Test
    void testShowSignupForm() {
        String viewName = webController.showSignupForm(model);
        assertEquals("signup", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void testSignUp_Success() {
        User user = new User();
        user.setName("Ali");
        when(userRepository.findByUname(user.getUname())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        String viewName = webController.signUp(user, model);
        assertEquals("welcome", viewName);
        verify(model).addAttribute("name", "Ali");
        verify(userRepository).save(user);
    }

    @Test
    void testSignUp_UsernameAlreadyExists() {
        User user = new User();
        user.setUname("existingUser");

        when(userRepository.findByUname("existingUser")).thenReturn(Optional.of(user));

        String viewName = webController.signUp(user, model);
        assertEquals("signup", viewName);
        verify(model).addAttribute("error", "Username already exists");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        User user = new User();
        user.setId(1L);
        user.setUname("Ali");
        user.setName("Ali");

        when(userService.findByUname("Ali")).thenReturn(Optional.of(user));

        String viewName = webController.login("Ali", model, session);
        assertEquals("welcome", viewName);
        verify(session).setAttribute("userId", 1L);
//        verify(session).setAttribute("username", "Ali");
//        verify(model).addAttribute("name", "Ali");
    }

    @Test
    void testLogin_Failure() {
        when(userService.findByUname("Ali")).thenReturn(Optional.empty());

        String viewName = webController.login("Ali", model, session);
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Username not found. Please sign up.");
    }

    @Test
    void testWelcomePage_LoggedIn() {
        User user = new User();
        user.setName("Ali");

        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        String viewName = webController.welcomePage(model, session);
        assertEquals("welcome", viewName);
        verify(model).addAttribute("name", "Ali");
    }

    @Test
    void testWelcomePage_NotLoggedIn() {
        when(session.getAttribute("userId")).thenReturn(null);

        String viewName = webController.welcomePage(model, session);
        assertEquals("login", viewName);
    }

    @Test
    void testWelcomePage_UserNotFound() {
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(Optional.empty());

        String viewName = webController.welcomePage(model, session);
        assertEquals("login", viewName);
    }


    @Test
    void testListUsers_NoSort() {
        String viewName = webController.listUsers(null, model);
        assertEquals("users", viewName);
        verify(userService).findAllUsers();
        verify(model).addAttribute(eq("users"), anyList());
    }

    @Test
    void testListUsers_SortByName() {
        String viewName = webController.listUsers("name", model);
        assertEquals("users", viewName);
        verify(userService).findAllUsersSortedByName();
        verify(model).addAttribute(eq("users"), anyList());
    }

    @Test
    void testEditUserForm_ValidUser() {
        User user = new User();
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        String viewName = webController.editUserForm(1L, model);
        assertEquals("user-edit-form", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testEditUserForm_InvalidUser() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            webController.editUserForm(1L, model);
        });
        assertEquals("Invalid user Id:1", exception.getMessage());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        String viewName = webController.updateUser(1L, user);
        assertEquals("redirect:/users", viewName);
        verify(userService).updateUser(user);
    }

    @Test
    void testDeleteUser() {
        String viewName = webController.deleteUser(1L);
        assertEquals("redirect:/users", viewName);
        verify(userService).deleteUser(1L);
    }

    @Test
    void testQuizPage() {
        String viewName = webController.quizPage();
        assertEquals("quiz", viewName);
    }

    @Test
    void testSubmitQuiz() {
        ResponseEntity<String> response = webController.submitQuiz(100);
        assertEquals("Score submitted successfully. Your score: 100", response.getBody());
    }

    @Test
    void testLogout() {
        String viewName = webController.logout(session);
        assertEquals("redirect:/login", viewName);
        verify(session).removeAttribute("userId");
        verify(session).removeAttribute("username");
    }


    
    
    @Test
    void testLogin_NullUsername() {
        String viewName = webController.login(null, model, session);
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Username not found. Please sign up.");
    }

	
    @Test
    void testLogin_LockedUser() {
        User user = new User();
        user.setLocked(true);  
        when(userService.findByUname("Ali")).thenReturn(Optional.of(user));

        String viewName = webController.login("Ali", model, session);
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Your account is locked.");
    }

    @Test
    public void testWelcomePage_UserNotLoggedIn() {
        when(session.getAttribute("userId")).thenReturn(null);
        String view = webController.welcomePage(model, session);
        assertEquals("login", view);
    }

    
    
    @Test
	void testEditUserForm_InvalidId() {
	    when(userService.findById(999L)).thenReturn(Optional.empty());
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        webController.editUserForm(999L, model);
	    });
	    assertEquals("Invalid user Id:999", exception.getMessage());
	}

    
}
