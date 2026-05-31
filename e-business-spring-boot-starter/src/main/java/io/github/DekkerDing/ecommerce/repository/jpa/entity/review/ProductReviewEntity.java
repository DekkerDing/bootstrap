package io.github.DekkerDing.ecommerce.repository.jpa.entity.review;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ecommerce_product_review")
public class ProductReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "reply_content", columnDefinition = "TEXT")
    private String replyContent;
    
    @Column(name = "reply_time")
    private LocalDateTime replyTime;
    
    @Column(name = "helpful_count", nullable = false)
    private Integer helpfulCount = 0;
    
    @Column(name = "status", length = 20)
    private String status;
    
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
}
