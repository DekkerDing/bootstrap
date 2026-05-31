package io.github.DekkerDing.ecommerce.repository.jpa.entity;

import javax.persistence.*;
import io.github.DekkerDing.ecommerce.domain.coupon.CouponType;
import io.github.DekkerDing.ecommerce.domain.coupon.UserCouponStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ecommerce_user_coupon")
public class UserCouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "coupon_no", nullable = false, length = 50)
    private String couponNo;

    @Column(name = "type", length = 20)
    private CouponType type;

    @Column(name = "coupon_name", length = 100)
    private String couponName;

    @Column(name = "status", nullable = false, length = 20)
    private UserCouponStatus status;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "obtained_at", nullable = false)
    private LocalDateTime obtainedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @Column(name = "min_amount", precision = 10, scale = 2)
    private BigDecimal minAmount;

    @PrePersist
    protected void onCreate() {
        obtainedAt = LocalDateTime.now();
    }
}
