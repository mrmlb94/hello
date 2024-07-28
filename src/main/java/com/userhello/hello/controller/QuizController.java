package com.userhello.hello.controller;

import com.userhello.hello.Service.QuizService;
import com.userhello.hello.model.QuizResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> submitQuiz(@RequestBody QuizResult submission, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }
        submission.setUsername(username); // Set the username from session
        submission.setTimestamp(new Date()); // Explicitly set the current date and time
        QuizResult result = quizService.saveQuizResult(submission);
        return ResponseEntity.ok(result);
    }
}