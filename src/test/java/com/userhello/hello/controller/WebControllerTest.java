package com.userhello.hello.controller;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.model.User;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WebControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private WebController webController;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(webController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testShowLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testShowSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    public void testSignUpSuccess() throws Exception {
        User user = new User();
        user.setName("test");
        user.setUname("test");

        when(userService.signUp(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "test")
                        .param("uname", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("name", "test"));
    }

    @Test
    public void testSignUpFailure() throws Exception {
        when(userService.signUp(any(User.class))).thenThrow(new RuntimeException("Username already exists"));

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "test")
                        .param("uname", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attribute("error", "Username already exists"));
    }

    @Test
    public void testSignUpControllerMethod() throws Exception {
        User user = new User();
        user.setUname("newuser");

        // Mocking the UserService to return the user when signUp is called
        when(userService.signUp(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("uname", "newuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("name", user.getName()));

        verify(userService, times(1)).signUp(any(User.class));
    }

    @Test
    public void testSignUpControllerMethodFailure() throws Exception {
        User user = new User();
        user.setUname("existinguser");

        // Mocking the UserService to throw RuntimeException when signUp is called
        when(userService.signUp(any(User.class))).thenThrow(new RuntimeException("Username already exists"));

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("uname", "existinguser"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attribute("error", "Username already exists"));

        verify(userService, times(1)).signUp(any(User.class));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUname("test");
        user.setName("Test User");

        when(userService.findByUname("test")).thenReturn(Optional.of(user));
        when(session.getAttribute("username")).thenReturn("test");

        mockMvc.perform(post("/login")
                        .param("uname", "test")
                        .sessionAttr("username", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("name", "Test User"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.findByUname("test")).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .param("uname", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "Username not found. Please sign up."));
    }

    @Test
    public void testWelcomePageLoggedIn() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");

        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/welcome")
                        .sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("name", "Test User"));
    }

    @Test
    public void testWelcomePageNotLoggedIn() throws Exception {
        when(session.getAttribute("userId")).thenReturn(null);

        mockMvc.perform(get("/welcome"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testListUsers() throws Exception {
        List<User> users = Collections.singletonList(new User());
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    public void testEditUserForm() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/editUser/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit-form"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Updated User");

        when(userService.updateUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/editUser/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Updated User"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(post("/deleteUser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void testQuizPage() throws Exception {
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("quiz"));
    }

    @Test
    public void testSubmitQuiz() throws Exception {
        mockMvc.perform(post("/submitQuiz")
                        .param("score", "100"))
                .andExpect(status().isOk())
                .andExpect(content().string("Score submitted successfully. Your score: 100"));
    }

    @Test
    public void testLogout() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("username", "test");

        mockMvc.perform(get("/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        assertNull(session.getAttribute("userId"));
        assertNull(session.getAttribute("username"));
    }
}
