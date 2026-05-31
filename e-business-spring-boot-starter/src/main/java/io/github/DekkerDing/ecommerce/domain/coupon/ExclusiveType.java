package io.github.DekkerDing.ecommerce.domain.coupon;

/**
 * 互斥类型枚举
 * Exclusive Type Enum
 */
public enum ExclusiveType {

    /**
     * 完全互斥 - 与所有优惠券互斥
     * Mutually exclusive with all coupons
     */
    MUTUAL_EXCLUSIVE,

    /**
     * 同类型互斥 - 与同类型优惠券互斥
     * Exclusive with same type coupons
     */
    SAME_TYPE,

    /**
     * 不互斥 - 可与其他券叠加
     * Not exclusive, can stack with others
     */
    NONE
}
