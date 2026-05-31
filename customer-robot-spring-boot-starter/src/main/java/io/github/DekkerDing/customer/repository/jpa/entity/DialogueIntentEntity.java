package io.github.DekkerDing.customer.repository.jpa.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 对话意图 JPA 实体
 * Dialogue Intent JPA Entity
 */
@Entity
@Table(name = "customer_dialogue_intent")
public class DialogueIntentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intent_name", length = 100, nullable = false)
    private String intentName;

    @Column(name = "intent_code", length = 50, nullable = false, unique = true)
    private String intentCode;

    @Column(length = 500)
    private String keywords;

    @Column(name = "response_template", length = 1000)
    private String responseTemplate;

    @Column(name = "confidence_threshold", precision = 5, scale = 4)
    private Double confidenceThreshold = 0.8;

    @Column
    private Integer priority = 0;

    @Column(length = 20)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public DialogueIntentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentCode() {
        return intentCode;
    }

    public void setIntentCode(String intentCode) {
        this.intentCode = intentCode;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getResponseTemplate() {
        return responseTemplate;
    }

    public void setResponseTemplate(String responseTemplate) {
        this.responseTemplate = responseTemplate;
    }

    public Double getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public void setConfidenceThreshold(Double confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
