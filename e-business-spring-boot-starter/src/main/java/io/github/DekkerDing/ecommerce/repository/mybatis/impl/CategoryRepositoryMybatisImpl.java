package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.repository.CategoryRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.CategoryMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 商品分类 MyBatis 仓储实现
 * Category MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class CategoryRepositoryMybatisImpl implements CategoryRepository {

    private final CategoryMybatisMapper mapper;

    @Autowired
    public CategoryRepositoryMybatisImpl(CategoryMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        mapper.insert(category);
        return category;
    }

    @Override
    @Transactional
    public Category update(Category category) {
        category.setUpdatedAt(LocalDateTime.now());
        mapper.update(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        Category category = mapper.findById(id);
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Category> findByParentId(Long parentId) {
        if (parentId == null) {
            return findTopLevelCategories();
        }
        return mapper.findByParentId(parentId);
    }

    @Override
    public List<Category> findTopLevelCategories() {
        return mapper.findTopLevelCategories();
    }

    @Override
    public Optional<Category> findByName(String name) {
        Category category = mapper.findByName(name);
        return Optional.ofNullable(category);
    }

    @Override
    public boolean hasChildren(Long categoryId) {
        return mapper.hasChildren(categoryId);
    }
}
