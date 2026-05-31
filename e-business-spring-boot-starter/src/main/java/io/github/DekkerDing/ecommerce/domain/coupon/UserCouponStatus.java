package io.github.DekkerDing.ecommerce.domain.coupon;

/**
 * 用户优惠券状态枚举
 * User Coupon Status Enum
 */
public enum UserCouponStatus {

    /**
     * 可用
     * Available to use
     */
    AVAILABLE,

    /**
     * 已使用
     * Already used
     */
    USED,

    /**
     * 已过期
     * Expired
     */
    EXPIRED,

    /**
     * 已冻结
     * Frozen
     */
    FROZEN
}
