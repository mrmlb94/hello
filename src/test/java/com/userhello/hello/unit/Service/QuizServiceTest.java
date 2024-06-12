package com.userhello.hello.unit.Service;

import com.userhello.hello.Service.QuizService;
import com.userhello.hello.model.QuizResult;
import com.userhello.hello.repository.QuizResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizResultRepository quizResultRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    void testSaveQuizResult() {
        QuizResult quizResult = new QuizResult("user1", 80);
        when(quizResultRepository.save(any(QuizResult.class))).thenReturn(quizResult);

        QuizResult savedResult = quizService.saveQuizResult(quizResult);

        assertNotNull(savedResult);
        assertEquals("user1", savedResult.getUsername());
        assertEquals(80, savedResult.getScore());
        verify(quizResultRepository).save(quizResult);
    }

    @Test
    void testSaveQuizResultWithException() {
        QuizResult quizResult = new QuizResult("user1", 80);
        when(quizResultRepository.save(any(QuizResult.class))).thenThrow(new DataAccessException("Failed to access data") {});

        Exception exception = assertThrows(DataAccessException.class, () -> {
            quizService.saveQuizResult(quizResult);
        });

        assertEquals("Failed to access data", exception.getMessage());
    }

    @Test
    void testSaveQuizResultBoundaryScores() {
        // Test with the minimum score
        QuizResult lowScoreResult = new QuizResult("user2", 0);
        when(quizResultRepository.save(lowScoreResult)).thenReturn(lowScoreResult);
        QuizResult savedLowScoreResult = quizService.saveQuizResult(lowScoreResult);
        assertEquals(0, savedLowScoreResult.getScore());

        // Test with the maximum score
        QuizResult highScoreResult = new QuizResult("user3", 100);
        when(quizResultRepository.save(highScoreResult)).thenReturn(highScoreResult);
        QuizResult savedHighScoreResult = quizService.saveQuizResult(highScoreResult);
        assertEquals(100, savedHighScoreResult.getScore());
    }
}
/***
testSaveQuizResult: This is a test, which verifies that saving a QuizResult works as expected.
 testSaveQuizResultWithException: Tests the behavior of QuizService when the underlying repository throws an exception.
 This is critical for ensuring the service can handle unexpected failures gracefully.
testSaveQuizResultBoundaryScores: This test checks how the service handles edge cases for scores,
 specifically the minimum and maximum valid scores.
 It's a good practice to test boundary conditions to ensure data integrity.
***/