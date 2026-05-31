package io.github.DekkerDing.customer.domain.dialogue;

import java.time.LocalDateTime;
import java.util.Map;

public class DialogueLog {
    private Long id;
    private Long conversationId;
    private Long userId;
    private String userInput;
    private String botResponse;
    private String intent;
    private Double confidence;
    private Map<String, Object> entities;
    private LocalDateTime createdAt;
    
    public DialogueLog() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConversationId() { return conversationId; }
    public void setConversationId(Long conversationId) { this.conversationId = conversationId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserInput() { return userInput; }
    public void setUserInput(String userInput) { this.userInput = userInput; }
    public String getBotResponse() { return botResponse; }
    public void setBotResponse(String botResponse) { this.botResponse = botResponse; }
    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    public Map<String, Object> getEntities() { return entities; }
    public void setEntities(Map<String, Object> entities) { this.entities = entities; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
