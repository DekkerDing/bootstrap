package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商品分类 MyBatis Mapper 接口
 * Category MyBatis Mapper Interface
 */
@Mapper
public interface CategoryMybatisMapper {

    /**
     * 插入分类
     * Insert category
     */
    @Insert("INSERT INTO ecommerce_category (name, parent_id, created_at, updated_at) " +
            "VALUES (#{name}, #{parentId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    /**
     * 更新分类
     * Update category
     */
    @Update("UPDATE ecommerce_category SET name = #{name}, parent_id = #{parentId}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(Category category);

    /**
     * 根据 ID 删除分类
     * Delete category by ID
     */
    @Delete("DELETE FROM ecommerce_category WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询分类
     * Find category by ID
     */
    @Select("SELECT * FROM ecommerce_category WHERE id = #{id}")
    @ResultMap("categoryResultMap")
    Category findById(Long id);

    /**
     * 查询所有分类
     * Find all categories
     */
    @Select("SELECT * FROM ecommerce_category ORDER BY created_at DESC")
    @ResultMap("categoryResultMap")
    List<Category> findAll();

    /**
     * 根据父级 ID 查询子分类列表
     * Find categories by parent ID
     */
    @Select("SELECT * FROM ecommerce_category WHERE parent_id = #{parentId} ORDER BY created_at DESC")
    @ResultMap("categoryResultMap")
    List<Category> findByParentId(Long parentId);

    /**
     * 查询顶级分类
     * Find top-level categories
     */
    @Select("SELECT * FROM ecommerce_category WHERE parent_id IS NULL ORDER BY created_at DESC")
    @ResultMap("categoryResultMap")
    List<Category> findTopLevelCategories();

    /**
     * 根据名称查询分类
     * Find category by name
     */
    @Select("SELECT * FROM ecommerce_category WHERE name = #{name}")
    @ResultMap("categoryResultMap")
    Category findByName(String name);

    /**
     * 检查分类是否存在子分类
     * Check if category has children
     */
    @Select("SELECT COUNT(*) > 0 FROM ecommerce_category WHERE parent_id = #{categoryId}")
    boolean hasChildren(Long categoryId);
}
