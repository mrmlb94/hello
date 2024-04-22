package com.userhello.hello.model;
import com.userhello.hello.model.QuizResult;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "score")
    private int score;

    @Temporal(TemporalType.TIMESTAMP) // This annotation is used to specify the exact time.
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Date timestamp; // This will store the timestamp when the quiz was taken.

    // Constructors
    public QuizResult() {
        this.timestamp = new Date(); // Initialize timestamp with current time upon creation
    }

    public QuizResult(String username, int score) {
        this.username = username;
        this.score = score;
        this.timestamp = new Date(); // Initialize timestamp with current time upon creation
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
