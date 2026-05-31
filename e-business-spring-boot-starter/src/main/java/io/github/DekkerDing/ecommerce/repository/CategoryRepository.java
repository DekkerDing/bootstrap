package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.product.Category;

import java.util.List;
import java.util.Optional;

/**
 * 商品分类仓储接口
 * Category Repository Interface
 * <p>
 * 框架无关的商品分类数据访问抽象
 * Framework-agnostic category data access abstraction
 * </p>
 */
public interface CategoryRepository {

    /**
     * 保存分类
     * Save category
     *
     * @param category 分类 / category
     * @return 保存后的分类 / saved category
     */
    Category save(Category category);

    /**
     * 更新分类
     * Update category
     *
     * @param category 分类 / category
     * @return 更新后的分类 / updated category
     */
    Category update(Category category);

    /**
     * 根据 ID 删除分类
     * Delete category by ID
     *
     * @param id 分类 ID / category ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询分类
     * Find category by ID
     *
     * @param id 分类 ID / category ID
     * @return 分类 / category
     */
    Optional<Category> findById(Long id);

    /**
     * 查询所有分类
     * Find all categories
     *
     * @return 分类列表 / category list
     */
    List<Category> findAll();

    /**
     * 根据父级 ID 查询子分类列表
     * Find categories by parent ID
     *
     * @param parentId 父级 ID / parent ID
     * @return 子分类列表 / child category list
     */
    List<Category> findByParentId(Long parentId);

    /**
     * 查询顶级分类
     * Find top-level categories
     *
     * @return 顶级分类列表 / top-level category list
     */
    List<Category> findTopLevelCategories();

    /**
     * 根据名称查询分类
     * Find category by name
     *
     * @param name 分类名称 / category name
     * @return 分类 / category
     */
    Optional<Category> findByName(String name);

    /**
     * 检查分类是否存在子分类
     * Check if category has children
     *
     * @param categoryId 分类 ID / category ID
     * @return 是否存在子分类 / whether has children
     */
    boolean hasChildren(Long categoryId);
}
