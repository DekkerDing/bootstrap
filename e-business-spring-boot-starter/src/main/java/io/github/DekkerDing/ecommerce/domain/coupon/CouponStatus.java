package io.github.DekkerDing.ecommerce.domain.coupon;

/**
 * 优惠券状态枚举
 * Coupon Status Enum
 */
public enum CouponStatus {

    /**
     * 待生效
     * Pending - not yet started
     */
    PENDING,

    /**
     * 进行中
     * Active - currently available
     */
    ACTIVE,

    /**
     * 已过期
     * Expired - past valid time
     */
    EXPIRED,

    /**
     * 已领完
     * Depleted - all issued
     */
    DEPLETED,

    /**
     * 已作废
     * Disabled - manually cancelled
     */
    DISABLED
}
