package com.userhello.hello.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userhello.hello.controller.QuizController;
import com.userhello.hello.model.QuizResult;
import com.userhello.hello.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    private final ObjectMapper mapper = new ObjectMapper();
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("username", "testUser");
    }

    @Test
    void testApiRoot() throws Exception {
        mockMvc.perform(get("/api"))
            .andExpect(status().isOk())
            .andExpect(content().string("API is up and running"));
    }

    @Test
    void testSubmitQuiz_UserNotLoggedIn() throws Exception {
        MockHttpSession emptySession = new MockHttpSession();

        QuizResult submission = new QuizResult();
        submission.setScore(90);

        mockMvc.perform(post("/api/submitQuiz")
                .session(emptySession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submission)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("User is not logged in."));

        verify(quizService, never()).saveQuizResult(any());
    }

    @Test
    void testSubmitQuiz_UserLoggedIn_SuccessfulSubmission() throws Exception {
        QuizResult submission = new QuizResult();
        submission.setScore(85);

        QuizResult savedSubmission = new QuizResult("testUser", 85);
//        savedSubmission.setTimestamp(new Date());

        when(quizService.saveQuizResult(any(QuizResult.class))).thenReturn(savedSubmission);

        mockMvc.perform(post("/api/submitQuiz")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submission)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("testUser"))
            .andExpect(jsonPath("$.score").value(85));
//            .andExpect(jsonPath("$.timestamp").exists());

        verify(quizService, times(1)).saveQuizResult(any());
    }
}
