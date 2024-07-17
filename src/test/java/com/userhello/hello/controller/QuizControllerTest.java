package com.userhello.hello.controller;

import com.userhello.hello.Service.QuizService;
import com.userhello.hello.model.QuizResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QuizControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();
    }

    @Test
    public void testSubmitQuiz() throws Exception {
        QuizResult submission = new QuizResult();
        submission.setId(1L);
        submission.setUsername("testuser");
        submission.setScore(100);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("username", "testuser");

        when(quizService.saveQuizResult(any(QuizResult.class))).thenReturn(submission);

        mockMvc.perform(post("/api/submitQuiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":100}")
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.score").value(100));

        verify(quizService, times(1)).saveQuizResult(any(QuizResult.class));
    }
}
