package io.github.DekkerDing.ecommerce.domain.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户优惠券领域模型
 * User Coupon Domain Model
 */
public class UserCoupon {

    /**
     * 用户优惠券唯一标识
     * Unique user coupon identifier
     */
    private Long id;

    /**
     * 用户 ID
     * User ID
     */
    private Long userId;

    /**
     * 优惠券 ID
     * Coupon ID
     */
    private Long couponId;

    /**
     * 优惠券编号
     * Coupon number
     */
    private String couponNo;

    /**
     * 优惠券类型
     * Coupon type
     */
    private CouponType type;

    /**
     * 优惠券名称
     * Coupon name
     */
    private String couponName;

    /**
     * 状态
     * Status: AVAILABLE, USED, EXPIRED
     */
    private UserCouponStatus status;

    /**
     * 使用订单 ID
     * Order ID when used
     */
    private Long orderId;

    /**
     * 获得时间
     * Obtained timestamp
     */
    private LocalDateTime obtainedAt;

    /**
     * 使用时间
     * Used timestamp
     */
    private LocalDateTime usedAt;

    /**
     * 过期时间
     * Expiration timestamp
     */
    private LocalDateTime expireAt;

    /**
     * 优惠金额（快照）
     * Discount amount snapshot
     */
    private BigDecimal discountAmount;

    /**
     * 折扣率（快照）
     * Discount rate snapshot
     */
    private Integer discountRate;

    /**
     * 最低消费（快照）
     * Minimum amount snapshot
     */
    private BigDecimal minAmount;

    public UserCoupon() {
        this.obtainedAt = LocalDateTime.now();
    }

    /**
     * 检查优惠券是否可用
     * Check if user coupon is available
     */
    public boolean isAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return status == UserCouponStatus.AVAILABLE
                && (expireAt == null || now.isBefore(expireAt) || now.isEqual(expireAt));
    }

    /**
     * 检查优惠券是否已过期
     * Check if user coupon is expired
     */
    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return expireAt != null && now.isAfter(expireAt);
    }

    /**
     * 使用优惠券
     * Use the coupon
     */
    public void use(Long orderId) {
        this.status = UserCouponStatus.USED;
        this.orderId = orderId;
        this.usedAt = LocalDateTime.now();
    }

    /**
     * 计算优惠金额
     * Calculate discount amount
     */
    public BigDecimal calculateDiscount(BigDecimal orderAmount) {
        if (type == CouponType.FULL_REDUCTION) {
            BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
            // 优惠金额不能超过订单金额
            if (discount.compareTo(orderAmount) > 0) {
                discount = orderAmount;
            }
            return discount;
        } else if (type == CouponType.DISCOUNT) {
            if (discountRate == null) {
                return BigDecimal.ZERO;
            }
            BigDecimal rate = new BigDecimal(discountRate).divide(new BigDecimal(100));
            return orderAmount.multiply(rate);
        }
        return BigDecimal.ZERO;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public UserCouponStatus getStatus() {
        return status;
    }

    public void setStatus(UserCouponStatus status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getObtainedAt() {
        return obtainedAt;
    }

    public void setObtainedAt(LocalDateTime obtainedAt) {
        this.obtainedAt = obtainedAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }
}
