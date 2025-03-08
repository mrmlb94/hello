package com.userhello.hello.controller;

import com.userhello.hello.service.QuizService;
import com.userhello.hello.model.QuizResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    // Constructor injection
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    
    @GetMapping
    public ResponseEntity<String> apiRoot() {
        return ResponseEntity.ok("API is up and running");
    }


    @PostMapping("/submitQuiz")
    public ResponseEntity<Object> submitQuiz(@RequestBody QuizResult submission, HttpSession session) {
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
