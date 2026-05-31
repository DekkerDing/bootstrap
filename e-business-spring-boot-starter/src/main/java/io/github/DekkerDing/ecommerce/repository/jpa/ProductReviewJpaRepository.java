package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewJpaRepository extends JpaRepository<ProductReviewEntity, Long> {
    List<ProductReviewEntity> findByProductId(Long productId);
    List<ProductReviewEntity> findByUserId(Long userId);
    List<ProductReviewEntity> findByProductIdAndStatus(Long productId, String status);
}
