package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.member.Member;
import io.github.DekkerDing.ecommerce.domain.member.PointsLog;
import io.github.DekkerDing.ecommerce.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员 API 控制器
 * Member Controller
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 获取会员信息
     * Get member info
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Member>> getMember(@PathVariable Long userId) {
        Member member = memberService.getOrCreateMember(userId);
        return ResponseEntity.ok(ApiResponse.success(member));
    }

    /**
     * 增加积分
     * Add points
     */
    @PostMapping("/{userId}/points")
    public ResponseEntity<ApiResponse<Member>> addPoints(
            @PathVariable Long userId,
            @RequestParam Integer points,
            @RequestParam(defaultValue = "EARN") String type,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(required = false) Long referenceId,
            @RequestParam(defaultValue = "") String referenceType) {
        Member member = memberService.addPoints(userId, points, type, description, referenceId, referenceType);
        return ResponseEntity.ok(ApiResponse.success(member));
    }

    /**
     * 扣减积分
     * Deduct points
     */
    @PostMapping("/{userId}/points/deduct")
    public ResponseEntity<ApiResponse<Member>> deductPoints(
            @PathVariable Long userId,
            @RequestParam Integer points,
            @RequestParam(defaultValue = "REDEEM") String type,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(required = false) Long referenceId,
            @RequestParam(defaultValue = "") String referenceType) {
        Member member = memberService.deductPoints(userId, points, type, description, referenceId, referenceType);
        return ResponseEntity.ok(ApiResponse.success(member));
    }

    /**
     * 增加成长值
     * Add growth value
     */
    @PostMapping("/{userId}/growth")
    public ResponseEntity<ApiResponse<Member>> addGrowthValue(
            @PathVariable Long userId,
            @RequestParam Integer value) {
        Member member = memberService.addGrowthValue(userId, value);
        return ResponseEntity.ok(ApiResponse.success(member));
    }

    /**
     * 获取积分日志
     * Get points log
     */
    @GetMapping("/{userId}/points/logs")
    public ResponseEntity<ApiResponse<List<PointsLog>>> getPointsLogs(@PathVariable Long userId) {
        List<PointsLog> logs = memberService.getPointsLogs(userId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    /**
     * 检查会员是否过期
     * Check if member is expired
     */
    @GetMapping("/{userId}/expired")
    public ResponseEntity<ApiResponse<Boolean>> checkExpired(@PathVariable Long userId) {
        return memberService.findByUserId(userId)
                .map(member -> ResponseEntity.ok(ApiResponse.success(memberService.isExpired(member))))
                .orElse(ResponseEntity.ok(ApiResponse.success(false)));
    }
}
