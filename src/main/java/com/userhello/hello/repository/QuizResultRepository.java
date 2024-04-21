package com.userhello.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.userhello.hello.model.QuizResult;
import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    // Method to find quiz results by username
    List<QuizResult> findByUsername(String username);
}
