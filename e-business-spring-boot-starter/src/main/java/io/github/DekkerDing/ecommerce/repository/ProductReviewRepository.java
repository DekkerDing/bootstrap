package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.review.ProductReview;
import java.util.List;
import java.util.Optional;

public interface ProductReviewRepository {
    Optional<ProductReview> findById(Long id);
    List<ProductReview> findByProductId(Long productId);
    List<ProductReview> findByUserId(Long userId);
    ProductReview save(ProductReview review);
    void deleteById(Long id);
}
