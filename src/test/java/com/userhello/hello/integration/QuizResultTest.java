package com.userhello.hello.integration;

import com.userhello.hello.model.QuizResult;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class QuizResultTest {

    @Test
    void testDefaultConstructor() {
        QuizResult quizResult = new QuizResult();
        assertNotNull(quizResult.getTimestamp(), "Timestamp should be initialized");
    }

    @Test
    void testParameterizedConstructor() {
        String username = "testUser";
        int score = 85;
        QuizResult quizResult = new QuizResult(username, score);

        assertEquals(username, quizResult.getUsername(), "Username should be set correctly");
        assertEquals(score, quizResult.getScore(), "Score should be set correctly");
        assertNotNull(quizResult.getTimestamp(), "Timestamp should be initialized");
    }

    @Test
    void testGetId() {
        QuizResult quizResult = new QuizResult();
        Long id = 1L;
        quizResult.setId(id);

        assertEquals(id, quizResult.getId(), "Id should be set correctly");
    }

    @Test
    void testSetId() {
        QuizResult quizResult = new QuizResult();
        Long id = 2L; // Different value from testGetId
        quizResult.setId(id);

        assertEquals(id, quizResult.getId(), "Id should be set correctly");
    }

    @Test
    void testGetUsername() {
        QuizResult quizResult = new QuizResult();
        String username = "testUser";
        quizResult.setUsername(username);

        assertEquals(username, quizResult.getUsername(), "Username should be set correctly");
    }

    @Test
    void testSetUsername() {
        QuizResult quizResult = new QuizResult();
        String username = "anotherUser"; // Different value from testGetUsername
        quizResult.setUsername(username);

        assertEquals(username, quizResult.getUsername(), "Username should be set correctly");
    }

    @Test
    void testGetScore() {
        QuizResult quizResult = new QuizResult();
        int score = 85;
        quizResult.setScore(score);

        assertEquals(score, quizResult.getScore(), "Score should be set correctly");
    }

    @Test
    void testSetScore() {
        QuizResult quizResult = new QuizResult();
        int score = 90; // Different value from testGetScore
        quizResult.setScore(score);

        assertEquals(score, quizResult.getScore(), "Score should be set correctly");
    }

    @Test
    void testGetTimestamp() {
        // Arrange
        QuizResult quizResult = new QuizResult();
        Date timestamp = new Date();

        // Act
        quizResult.setTimestamp(timestamp);

        // Assert
        assertEquals(timestamp, quizResult.getTimestamp(), "Timestamp should be set correctly");
    }

    @Test
    void testSetTimestamp() {
        // Arrange
        QuizResult quizResult = new QuizResult();
        Date timestamp1 = new Date();
        Date timestamp2 = new Date(timestamp1.getTime() + 1000); // A different timestamp

        // Act
        quizResult.setTimestamp(timestamp1);
        quizResult.setTimestamp(timestamp2);

        // Assert
        assertEquals(timestamp2, quizResult.getTimestamp(), "Timestamp should be updated correctly");
    }
}
