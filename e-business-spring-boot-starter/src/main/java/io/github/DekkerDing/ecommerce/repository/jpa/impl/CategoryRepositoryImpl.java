package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.repository.CategoryRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.CategoryJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.CategoryEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 商品分类 JPA 仓储实现
 * Category JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    @Autowired
    public CategoryRepositoryImpl(CategoryJpaRepository jpaRepository, CategoryMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        CategoryEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        entity.setId(category.getId());
        CategoryEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public List<Category> findByParentId(Long parentId) {
        if (parentId == null) {
            return findTopLevelCategories();
        }
        return mapper.toDomainList(jpaRepository.findByParentId(parentId));
    }

    @Override
    public List<Category> findTopLevelCategories() {
        return mapper.toDomainList(jpaRepository.findByParentIdIsNull());
    }

    @Override
    public Optional<Category> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public boolean hasChildren(Long categoryId) {
        return jpaRepository.hasChildren(categoryId);
    }
}
