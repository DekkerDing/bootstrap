package io.github.DekkerDing.customer.domain.conversation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 对话领域模型
 * Conversation Domain Model
 */
public class Conversation {

    /**
     * 对话唯一标识
     * Unique conversation identifier
     */
    private Long id;

    /**
     * 对话编号
     * Conversation number
     */
    private String conversationNo;

    /**
     * 客户 ID
     * Customer ID
     */
    private Long customerId;

    /**
     * 客服 ID
     * Agent ID
     */
    private Long agentId;

    /**
     * 对话状态（WAITING/IN_PROGRESS/CLOSED）
     * Conversation status
     */
    private String status;

    /**
     * 对话类型（TEXT/VOICE/VIDEO）
     * Conversation type
     */
    private String type;

    /**
     * 优先级（LOW/NORMAL/HIGH/URGENT）
     * Priority
     */
    private String priority;

    /**
     * 标题
     * Title
     */
    private String title;

    /**
     * 消息列表
     * Message list
     */
    private List<io.github.DekkerDing.customer.domain.message.Message> messages;

    /**
     * 开始时间
     * Start time
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     * End time
     */
    private LocalDateTime endTime;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     * Last update timestamp
     */
    private LocalDateTime updatedAt;

    public Conversation() {
        this.status = "WAITING";
        this.type = "TEXT";
        this.priority = "NORMAL";
        this.startTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.messages = new ArrayList<>();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConversationNo() {
        return conversationNo;
    }

    public void setConversationNo(String conversationNo) {
        this.conversationNo = conversationNo;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
        if ("CLOSED".equals(status)) {
            this.endTime = LocalDateTime.now();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public List<io.github.DekkerDing.customer.domain.message.Message> getMessages() {
        return messages;
    }

    public void setMessages(List<io.github.DekkerDing.customer.domain.message.Message> messages) {
        this.messages = messages;
        this.updatedAt = LocalDateTime.now();
    }

    public void addMessage(io.github.DekkerDing.customer.domain.message.Message message) {
        this.messages.add(message);
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        this.updatedAt = LocalDateTime.now();
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

    /**
     * 检查对话是否进行中
     * Check if conversation is in progress
     */
    public boolean isInProgress() {
        return "IN_PROGRESS".equals(this.status);
    }

    /**
     * 检查对话是否已关闭
     * Check if conversation is closed
     */
    public boolean isClosed() {
        return "CLOSED".equals(this.status);
    }

    /**
     * 分配客服
     * Assign agent
     */
    public void assignAgent(Long agentId) {
        this.agentId = agentId;
        this.status = "IN_PROGRESS";
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 关闭对话
     * Close conversation
     */
    public void close() {
        this.status = "CLOSED";
        this.endTime = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
