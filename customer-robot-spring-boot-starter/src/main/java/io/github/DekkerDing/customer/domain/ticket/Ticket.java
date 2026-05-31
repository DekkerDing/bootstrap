package io.github.DekkerDing.customer.domain.ticket;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private String ticketNo;
    private Long customerId;
    private Long conversationId;
    private String type;
    private String priority;
    private String status;
    private String title;
    private String description;
    private Long assignedTo;
    private Long resolvedBy;
    private String resolution;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
    
    public enum Status {
        PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, REJECTED
    }
    
    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }
    
    public Ticket() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void assignTo(Long agentId) {
        this.assignedTo = agentId;
        this.status = Status.ASSIGNED.name();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void resolve(Long resolverId, String resolution) {
        this.resolvedBy = resolverId;
        this.resolution = resolution;
        this.status = Status.RESOLVED.name();
        this.resolvedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void close() {
        this.status = Status.CLOSED.name();
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isEscalatable() {
        return Status.PENDING.name().equals(status) || Status.IN_PROGRESS.name().equals(status);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTicketNo() { return ticketNo; }
    public void setTicketNo(String ticketNo) { this.ticketNo = ticketNo; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getConversationId() { return conversationId; }
    public void setConversationId(Long conversationId) { this.conversationId = conversationId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }
    public Long getResolvedBy() { return resolvedBy; }
    public void setResolvedBy(Long resolvedBy) { this.resolvedBy = resolvedBy; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}
