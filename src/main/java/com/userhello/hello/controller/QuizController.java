package com.userhello.hello.controller;

import com.userhello.hello.Service.QuizService;
import com.userhello.hello.model.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> submitQuiz(@RequestBody QuizResult submission) {
        QuizResult result = quizService.saveQuizResult(submission);
        return ResponseEntity.ok(result);
    }
}
