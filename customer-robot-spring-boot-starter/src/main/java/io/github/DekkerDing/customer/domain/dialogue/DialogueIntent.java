package io.github.DekkerDing.customer.domain.dialogue;

import java.util.Map;

public class DialogueIntent {
    private Long id;
    private String intentName;
    private String intentCode;
    private String keywords;
    private String responseTemplate;
    private Double confidenceThreshold;
    private Integer priority;
    private String status;
    
    public IntentType getIntentType() {
        if (intentCode.startsWith("QUERY_")) return IntentType.QUERY;
        if (intentCode.startsWith("REQUEST_")) return IntentType.REQUEST;
        if (intentCode.equals("GREETING")) return IntentType.GREETING;
        if (intentCode.equals("COMPLAINT")) return IntentType.ESCALATION;
        return IntentType.UNKNOWN;
    }
    
    public enum IntentType {
        QUERY, REQUEST, GREETING, ESCALATION, UNKNOWN
    }
    
    public boolean matchKeyword(String input) {
        if (keywords == null || keywords.isEmpty()) return false;
        String[] keywordArray = keywords.split(",");
        for (String keyword : keywordArray) {
            if (input.toLowerCase().contains(keyword.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIntentName() { return intentName; }
    public void setIntentName(String intentName) { this.intentName = intentName; }
    public String getIntentCode() { return intentCode; }
    public void setIntentCode(String intentCode) { this.intentCode = intentCode; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    public String getResponseTemplate() { return responseTemplate; }
    public void setResponseTemplate(String responseTemplate) { this.responseTemplate = responseTemplate; }
    public Double getConfidenceThreshold() { return confidenceThreshold; }
    public void setConfidenceThreshold(Double confidenceThreshold) { this.confidenceThreshold = confidenceThreshold; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
