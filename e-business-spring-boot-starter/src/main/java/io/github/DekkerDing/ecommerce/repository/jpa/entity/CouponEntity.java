package io.github.DekkerDing.ecommerce.repository.jpa.entity;

import javax.persistence.*;
import io.github.DekkerDing.ecommerce.domain.coupon.CouponStatus;
import io.github.DekkerDing.ecommerce.domain.coupon.CouponType;
import io.github.DekkerDing.ecommerce.domain.coupon.ExclusiveType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ecommerce_coupon")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_no", nullable = false, unique = true, length = 50)
    private String couponNo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 20)
    private CouponType type;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @Column(name = "min_amount", precision = 10, scale = 2)
    private BigDecimal minAmount;

    @Column(name = "max_discount", precision = 10, scale = 2)
    private BigDecimal maxDiscount;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "issued_quantity", nullable = false)
    private Integer issuedQuantity = 0;

    @Column(name = "used_quantity", nullable = false)
    private Integer usedQuantity = 0;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false, length = 20)
    private CouponStatus status;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "limit_per_user")
    private Integer limitPerUser = 1;

    @Column(name = "exclusive_type", length = 20)
    private ExclusiveType exclusiveType;

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
