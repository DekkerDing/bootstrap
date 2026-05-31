package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.review.ProductReview;
import io.github.DekkerDing.ecommerce.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;

    @Autowired
    public ProductReviewService(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    public ProductReview createReview(ProductReview review) {
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        review.setStatus("PENDING");
        return productReviewRepository.save(review);
    }

    public Optional<ProductReview> findById(Long id) {
        return productReviewRepository.findById(id);
    }

    public List<ProductReview> findByProductId(Long productId) {
        return productReviewRepository.findByProductId(productId);
    }

    public List<ProductReview> findByUserId(Long userId) {
        return productReviewRepository.findByUserId(userId);
    }

    public ProductReview addReply(Long reviewId, String replyContent) {
        Optional<ProductReview> reviewOpt = productReviewRepository.findById(reviewId);
        if (!reviewOpt.isPresent()) {
            throw new IllegalArgumentException("评价不存在 / Review not found");
        }
        ProductReview review = reviewOpt.get();
        review.addReply(replyContent);
        review.setUpdatedAt(LocalDateTime.now());
        return productReviewRepository.save(review);
    }

    public ProductReview markHelpful(Long reviewId) {
        Optional<ProductReview> reviewOpt = productReviewRepository.findById(reviewId);
        if (!reviewOpt.isPresent()) {
            throw new IllegalArgumentException("评价不存在 / Review not found");
        }
        ProductReview review = reviewOpt.get();
        review.incrementHelpful();
        return productReviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }
}
