package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.coupon.Coupon;
import io.github.DekkerDing.ecommerce.domain.coupon.UserCoupon;
import io.github.DekkerDing.ecommerce.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;
    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Coupon>>> getAvailableCoupons() {
        return ResponseEntity.ok(ApiResponse.success(couponService.getAvailableCoupons()));
    }
    @PostMapping("/{couponNo}/claim")
    public ResponseEntity<ApiResponse<UserCoupon>> claimCoupon(
            @PathVariable String couponNo,
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(couponService.claimCoupon(userId, couponNo)));
    }
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<UserCoupon>>> getMyCoupons(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(couponService.getUserCoupons(userId)));
    }
}
