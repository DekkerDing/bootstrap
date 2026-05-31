package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.coupon.UserCoupon;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository {
    UserCoupon save(UserCoupon userCoupon);
    List<UserCoupon> findByUserId(Long userId);
    Optional<UserCoupon> findById(Long id);
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);
}
