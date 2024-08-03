package com.userhello.hello.unit;

import com.userhello.hello.model.QuizSubmission;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizSubmissionTest {

    @Test
    void testDefaultConstructor() {
        QuizSubmission quizSubmission = new QuizSubmission();
        assertNotNull(quizSubmission);
    }

    @Test
    void testParameterizedConstructor() {
        QuizSubmission quizSubmission = new QuizSubmission(85, "testuser");
        assertEquals(85, quizSubmission.getScore());
        assertEquals("testuser", quizSubmission.getUsername());
    }

    @Test
    void testGetAndSetScore() {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setScore(95);
        assertEquals(95, quizSubmission.getScore());
    }

    @Test
    void testGetAndSetUsername() {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setUsername("testuser");
        assertEquals("testuser", quizSubmission.getUsername());
    }
}
