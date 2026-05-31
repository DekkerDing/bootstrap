package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.coupon.Coupon;
import io.github.DekkerDing.ecommerce.domain.coupon.UserCoupon;
import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findByCouponNo(String couponNo);
    Coupon save(Coupon coupon);
    List<Coupon> findAvailableCoupons();
    Optional<Coupon> findById(Long id);
}
