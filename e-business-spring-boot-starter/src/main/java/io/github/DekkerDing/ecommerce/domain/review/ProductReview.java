package io.github.DekkerDing.ecommerce.domain.review;

import java.time.LocalDateTime;

public class ProductReview {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long userId;
    private String userName;
    private Integer rating;  // 1-5星
    private String content;
    private String replyContent;
    private LocalDateTime replyTime;
    private Integer helpfulCount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProductReview() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addReply(String reply) {
        this.replyContent = reply;
        this.replyTime = LocalDateTime.now();
    }
    
    public void incrementHelpful() {
        this.helpfulCount++;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getReplyContent() { return replyContent; }
    public LocalDateTime getReplyTime() { return replyTime; }
    public Integer getHelpfulCount() { return helpfulCount; }
    public void setHelpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
