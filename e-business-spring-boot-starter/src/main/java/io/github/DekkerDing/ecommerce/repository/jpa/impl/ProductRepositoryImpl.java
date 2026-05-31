package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.ProductJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品 JPA 仓储实现
 * Product JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Autowired
    public ProductRepositoryImpl(ProductJpaRepository jpaRepository, ProductMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        entity.setId(product.getId());
        ProductEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return mapper.toDomainList(jpaRepository.findByCategoryId(categoryId));
    }

    @Override
    public List<Product> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return mapper.toDomainList(jpaRepository.findByNameContaining(name));
    }

    @Override
    public boolean checkStockAvailable(Long productId, Integer quantity) {
        return jpaRepository.checkStockAvailable(productId, quantity).isPresent();
    }

    @Override
    @Transactional
    public void deductStock(Long productId, Integer quantity) {
        ProductEntity entity = jpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        if (entity.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        entity.setStock(entity.getStock() - quantity);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void restoreStock(Long productId, Integer quantity) {
        ProductEntity entity = jpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        entity.setStock(entity.getStock() + quantity);
        jpaRepository.save(entity);
    }
}
