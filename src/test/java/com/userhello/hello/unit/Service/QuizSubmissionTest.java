package com.userhello.hello.unit.Service;

import com.userhello.hello.model.QuizSubmission;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizSubmissionTest {

    @Test
    public void testDefaultConstructor() {
        QuizSubmission quizSubmission = new QuizSubmission();
        assertNotNull(quizSubmission);
    }

    @Test
    public void testParameterizedConstructor() {
        QuizSubmission quizSubmission = new QuizSubmission(85, "testuser");
        assertEquals(85, quizSubmission.getScore());
        assertEquals("testuser", quizSubmission.getUsername());
    }

    @Test
    public void testGetAndSetScore() {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setScore(95);
        assertEquals(95, quizSubmission.getScore());
    }

    @Test
    public void testGetAndSetUsername() {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setUsername("testuser");
        assertEquals("testuser", quizSubmission.getUsername());
    }
}
