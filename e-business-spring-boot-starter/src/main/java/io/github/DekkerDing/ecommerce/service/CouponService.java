package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.coupon.Coupon;
import io.github.DekkerDing.ecommerce.domain.coupon.UserCoupon;
import java.math.BigDecimal;
import java.util.List;

public interface CouponService {
    List<Coupon> getAvailableCoupons();
    Coupon getCouponByNo(String couponNo);
    UserCoupon claimCoupon(Long userId, String couponNo);
    UserCoupon useCoupon(Long userCouponId, Long orderId);
    List<UserCoupon> getUserCoupons(Long userId);
    BigDecimal calculateDiscount(String couponNo, BigDecimal orderAmount);
}
