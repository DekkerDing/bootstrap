package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.review.ProductReview;
import io.github.DekkerDing.ecommerce.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品评价 API 控制器
 * Product Review Controller
 */
@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    /**
     * 创建评价
     * Create review
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductReview>> createReview(@RequestBody ProductReview review) {
        ProductReview createdReview = productReviewService.createReview(review);
        return ResponseEntity.ok(ApiResponse.success(createdReview));
    }

    /**
     * 获取评价详情
     * Get review details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductReview>> getReview(@PathVariable Long id) {
        return productReviewService.findById(id)
                .map(review -> ResponseEntity.ok(ApiResponse.success(review)))
                .orElse(ResponseEntity.ok(ApiResponse.notFound("评价不存在 / Review not found")));
    }

    /**
     * 获取商品评价列表
     * Get product reviews
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<ProductReview>>> getProductReviews(@PathVariable Long productId) {
        List<ProductReview> reviews = productReviewService.findByProductId(productId);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    /**
     * 获取用户评价列表
     * Get user reviews
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ProductReview>>> getUserReviews(@PathVariable Long userId) {
        List<ProductReview> reviews = productReviewService.findByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    /**
     * 回复评价
     * Reply to review
     */
    @PostMapping("/{id}/reply")
    public ResponseEntity<ApiResponse<ProductReview>> replyReview(
            @PathVariable Long id,
            @RequestParam String replyContent) {
        ProductReview review = productReviewService.addReply(id, replyContent);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    /**
     * 标记评价有用
     * Mark review as helpful
     */
    @PostMapping("/{id}/helpful")
    public ResponseEntity<ApiResponse<ProductReview>> markHelpful(@PathVariable Long id) {
        ProductReview review = productReviewService.markHelpful(id);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    /**
     * 删除评价
     * Delete review
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        productReviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.<Void>success("评价已删除 / Review deleted", null));
    }
}
