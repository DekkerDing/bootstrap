package io.github.DekkerDing.customer.domain.message;

import java.time.LocalDateTime;

/**
 * 消息领域模型
 * Message Domain Model
 */
public class Message {

    /**
     * 消息唯一标识
     * Unique message identifier
     */
    private Long id;

    /**
     * 对话 ID
     * Conversation ID
     */
    private Long conversationId;

    /**
     * 发送者 ID（客户或客服）
     * Sender ID
     */
    private Long senderId;

    /**
     * 接收者 ID
     * Receiver ID
     */
    private Long receiverId;

    /**
     * 发送者类型（CUSTOMER/AGENT/SYSTEM）
     * Sender type
     */
    private String senderType;

    /**
     * 消息类型（TEXT/IMAGE/FILE/VOICE/VIDEO）
     * Message type
     */
    private String messageType;

    /**
     * 消息内容
     * Message content
     */
    private String content;

    /**
     * 是否已读
     * Is read
     */
    private Boolean isRead;

    /**
     * 读取时间
     * Read time
     */
    private LocalDateTime readAt;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    public Message() {
        this.messageType = "TEXT";
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
        if (isRead && this.readAt == null) {
            this.readAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 标记为已读
     * Mark as read
     */
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

    /**
     * 检查是否为系统消息
     * Check if is system message
     */
    public boolean isSystemMessage() {
        return "SYSTEM".equals(this.senderType);
    }

    /**
     * 检查是否为客户消息
     * Check if is customer message
     */
    public boolean isCustomerMessage() {
        return "CUSTOMER".equals(this.senderType);
    }

    /**
     * 检查是否为客服消息
     * Check if is agent message
     */
    public boolean isAgentMessage() {
        return "AGENT".equals(this.senderType);
    }
}
