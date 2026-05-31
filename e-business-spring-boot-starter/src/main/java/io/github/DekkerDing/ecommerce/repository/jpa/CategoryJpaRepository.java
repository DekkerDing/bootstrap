package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 商品分类 JPA 仓储接口
 * Category JPA Repository Interface
 */
@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * 根据父级 ID 查询子分类列表
     * Find categories by parent ID
     */
    List<CategoryEntity> findByParentId(Long parentId);

    /**
     * 查询顶级分类（父级 ID 为 null）
     * Find top-level categories
     */
    List<CategoryEntity> findByParentIdIsNull();

    /**
     * 根据名称查询分类
     * Find category by name
     */
    Optional<CategoryEntity> findByName(String name);

    /**
     * 检查分类是否存在子分类
     * Check if category has children
     */
    @Query("SELECT COUNT(c) > 0 FROM CategoryEntity c WHERE c.parentId = :categoryId")
    boolean hasChildren(@Param("categoryId") Long categoryId);
}
