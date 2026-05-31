package io.github.DekkerDing.ecommerce.domain.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券领域模型
 * Coupon Domain Model
 */
public class Coupon {

    /**
     * 优惠券唯一标识
     * Unique coupon identifier
     */
    private Long id;

    /**
     * 优惠券编号
     * Coupon number
     */
    private String couponNo;

    /**
     * 优惠券名称
     * Coupon name
     */
    private String name;

    /**
     * 优惠券类型
     * Coupon type: FULL_REDUCTION, DISCOUNT, FREE_SHIPPING
     */
    private CouponType type;

    /**
     * 优惠金额（满减券）
     * Discount amount for full reduction coupon
     */
    private BigDecimal discountAmount;

    /**
     * 折扣率（折扣券，百分比）
     * Discount rate for percentage discount coupon
     */
    private Integer discountRate;

    /**
     * 最低消费金额
     * Minimum order amount to use coupon
     */
    private BigDecimal minAmount = BigDecimal.ZERO;

    /**
     * 最大优惠金额
     * Maximum discount amount
     */
    private BigDecimal maxDiscount;

    /**
     * 发行总量
     * Total quantity to issue
     */
    private Integer totalQuantity;

    /**
     * 已发放数量
     * Quantity already issued
     */
    private Integer issuedQuantity = 0;

    /**
     * 已使用数量
     * Quantity already used
     */
    private Integer usedQuantity = 0;

    /**
     * 有效开始时间
     * Valid start time
     */
    private LocalDateTime startTime;

    /**
     * 有效结束时间
     * Valid end time
     */
    private LocalDateTime endTime;

    /**
     * 优惠券状态
     * Coupon status: PENDING, ACTIVE, EXPIRED, DEPLETED
     */
    private CouponStatus status;

    /**
     * 描述
     * Description
     */
    private String description;

    /**
     * 适用商品分类 ID（限制品类券）
     * Applicable category ID for category-specific coupons
     */
    private Long categoryId;

    /**
     * 适用商品 ID（限制商品券）
     * Applicable product ID for product-specific coupons
     */
    private Long productId;

    /**
     * 每人限领数量
     * Limit per user (0 = unlimited)
     */
    private Integer limitPerUser = 1;

    /**
     * 互斥类型
     * Exclusive type: MUTUAL_EXCLUSIVE, SAME_TYPE, NONE
     */
    private ExclusiveType exclusiveType = ExclusiveType.SAME_TYPE;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * Update timestamp
     */
    private LocalDateTime updatedAt;

    public Coupon() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 检查优惠券是否可用
     * Check if coupon is available
     */
    public boolean isAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return status == CouponStatus.ACTIVE
                && (startTime == null || now.isAfter(startTime) || now.isEqual(startTime))
                && (endTime == null || now.isBefore(endTime) || now.isEqual(endTime))
                && (totalQuantity == 0 || issuedQuantity < totalQuantity);
    }

    /**
     * 检查是否还有剩余库存
     * Check if there are remaining coupons
     */
    public boolean hasRemaining() {
        return totalQuantity == 0 || issuedQuantity < totalQuantity;
    }

    /**
     * 获取剩余数量
     * Get remaining quantity
     */
    public Integer getRemainingQuantity() {
        if (totalQuantity == 0) {
            return -1; // 无限制
        }
        return totalQuantity - issuedQuantity;
    }

    /**
     * 计算优惠金额
     * Calculate discount amount
     */
    public BigDecimal calculateDiscount(BigDecimal orderAmount) {
        if (type == CouponType.FULL_REDUCTION) {
            // 满减券
            if (orderAmount.compareTo(minAmount) < 0) {
                return BigDecimal.ZERO;
            }
            BigDecimal discount = discountAmount;
            if (maxDiscount != null && discount.compareTo(maxDiscount) > 0) {
                discount = maxDiscount;
            }
            return discount;
        } else if (type == CouponType.DISCOUNT) {
            // 折扣券
            BigDecimal discountRate = new BigDecimal(this.discountRate).divide(new BigDecimal(100));
            BigDecimal discount = orderAmount.multiply(discountRate);
            if (maxDiscount != null && discount.compareTo(maxDiscount) > 0) {
                discount = maxDiscount;
            }
            return discount;
        } else if (type == CouponType.FREE_SHIPPING) {
            // 免邮券（返回运费金额）
            return null; // 需要从物流服务获取运费
        }
        return BigDecimal.ZERO;
    }

    /**
     * 检查订单金额是否满足使用条件
     * Check if order amount meets usage requirement
     */
    public boolean meetsRequirement(BigDecimal orderAmount) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return orderAmount.compareTo(minAmount) >= 0;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
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

    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(Integer issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(Integer limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public ExclusiveType getExclusiveType() {
        return exclusiveType;
    }

    public void setExclusiveType(ExclusiveType exclusiveType) {
        this.exclusiveType = exclusiveType;
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
}
