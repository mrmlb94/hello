package com.userhello.hello.unit;

import com.userhello.hello.controller.QuizController;
import com.userhello.hello.model.QuizResult;
import com.userhello.hello.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @MockBean
    private HttpSession session;

    @Test
    void testControllerLoads() throws Exception {
        mockMvc.perform(get("/api")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSubmitQuiz_UserNotLoggedIn() {
        // Set up
        when(session.getAttribute("username")).thenReturn(null);
        QuizResult submission = new QuizResult();

        // Execute
        ResponseEntity<?> response = new QuizController(quizService).submitQuiz(submission, session);

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
        ResponseEntity<?> response = new QuizController(quizService).submitQuiz(submission, session);

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
