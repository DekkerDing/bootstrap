package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.review.ProductReview;
import io.github.DekkerDing.ecommerce.repository.ProductReviewRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.ProductReviewJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductReviewEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.ProductReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductReviewRepositoryImpl implements ProductReviewRepository {
    private final ProductReviewJpaRepository productReviewJpaRepository;
    private final ProductReviewMapper productReviewMapper;

    @Autowired
    public ProductReviewRepositoryImpl(ProductReviewJpaRepository productReviewJpaRepository,
                                       ProductReviewMapper productReviewMapper) {
        this.productReviewJpaRepository = productReviewJpaRepository;
        this.productReviewMapper = productReviewMapper;
    }

    @Override
    public Optional<ProductReview> findById(Long id) {
        return productReviewJpaRepository.findById(id)
                .map(productReviewMapper::toDomain);
    }

    @Override
    public List<ProductReview> findByProductId(Long productId) {
        return productReviewMapper.toDomainList(
                productReviewJpaRepository.findByProductId(productId)
        );
    }

    @Override
    public List<ProductReview> findByUserId(Long userId) {
        return productReviewMapper.toDomainList(
                productReviewJpaRepository.findByUserId(userId)
        );
    }

    @Override
    public ProductReview save(ProductReview review) {
        ProductReviewEntity entity = productReviewMapper.toEntity(review);
        ProductReviewEntity savedEntity = productReviewJpaRepository.save(entity);
        return productReviewMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        productReviewJpaRepository.deleteById(id);
    }
}
