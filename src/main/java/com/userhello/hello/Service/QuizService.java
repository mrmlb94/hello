package com.userhello.hello.Service;

import com.userhello.hello.model.QuizResult;
import com.userhello.hello.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizResultRepository quizResultRepository;
    @Autowired
    public QuizService(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }

    public QuizResult saveQuizResult(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }
}
