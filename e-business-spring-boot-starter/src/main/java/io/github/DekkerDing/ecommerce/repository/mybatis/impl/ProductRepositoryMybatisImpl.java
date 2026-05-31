package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.ProductMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 商品 MyBatis 仓储实现
 * Product MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class ProductRepositoryMybatisImpl implements ProductRepository {

    private final ProductMybatisMapper mapper;

    @Autowired
    public ProductRepositoryMybatisImpl(ProductMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        mapper.insert(product);
        return product;
    }

    @Override
    @Transactional
    public Product update(Product product) {
        product.setUpdatedAt(LocalDateTime.now());
        mapper.update(product);
        return product;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = mapper.findById(id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return mapper.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findByStatus(String status) {
        return mapper.findByStatus(status);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return mapper.findByNameContaining(name);
    }

    @Override
    public boolean checkStockAvailable(Long productId, Integer quantity) {
        return mapper.checkStockAvailable(productId, quantity) != null;
    }

    @Override
    @Transactional
    public void deductStock(Long productId, Integer quantity) {
        int rows = mapper.deductStock(productId, quantity);
        if (rows == 0) {
            throw new IllegalArgumentException("Insufficient stock or product not found");
        }
    }

    @Override
    @Transactional
    public void restoreStock(Long productId, Integer quantity) {
        mapper.restoreStock(productId, quantity);
    }
}
