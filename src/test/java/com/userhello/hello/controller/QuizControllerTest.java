package com.userhello.hello.controller;

import com.userhello.hello.service.QuizService;
import com.userhello.hello.model.QuizResult;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private QuizController quizController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();
    }

    @Test
    void testSubmitQuiz_UserNotLoggedIn() {
        // Set up
        when(session.getAttribute("username")).thenReturn(null);
        QuizResult submission = new QuizResult();

        // Execute
        ResponseEntity<?> response = quizController.submitQuiz(submission, session);

        // Verify
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User is not logged in.", response.getBody());
    }

    @Test
    void testSubmitQuiz_UserLoggedIn() {
        // Set up
        String username = "testUser";
        when(session.getAttribute("username")).thenReturn(username);
        QuizResult submission = new QuizResult();
        QuizResult savedSubmission = new QuizResult();
        savedSubmission.setUsername(username);
        savedSubmission.setTimestamp(new Date());

        when(quizService.saveQuizResult(any(QuizResult.class))).thenReturn(savedSubmission);

        // Execute
        ResponseEntity<?> response = quizController.submitQuiz(submission, session);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        QuizResult result = (QuizResult) response.getBody();
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertNotNull(result.getTimestamp());

        // Ensure the service method was called once
        verify(quizService, times(1)).saveQuizResult(any(QuizResult.class));
    }
}