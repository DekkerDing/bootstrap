package io.github.DekkerDing.customer.repository.jpa.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 对话日志 JPA 实体
 * Dialogue Log JPA Entity
 */
@Entity
@Table(name = "customer_dialogue_log")
public class DialogueLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userInput;

    @Column(name = "intent_detected", length = 50)
    private String intentDetected;

    @Column(name = "confidence_score", precision = 5, scale = 4)
    private Double confidenceScore;

    @Column(name = "bot_response", length = 2000)
    private String botResponse;

    @Column(name = "response_source", length = 50)
    private String responseSource;

    @Column(name = "escalated", nullable = false)
    private Boolean escalated = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public DialogueLogEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getIntentDetected() {
        return intentDetected;
    }

    public void setIntentDetected(String intentDetected) {
        this.intentDetected = intentDetected;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }

    public String getResponseSource() {
        return responseSource;
    }

    public void setResponseSource(String responseSource) {
        this.responseSource = responseSource;
    }

    public Boolean getEscalated() {
        return escalated;
    }

    public void setEscalated(Boolean escalated) {
        this.escalated = escalated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
