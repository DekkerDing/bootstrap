package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.product.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商品 MyBatis Mapper 接口
 * Product MyBatis Mapper Interface
 */
@Mapper
public interface ProductMybatisMapper {

    /**
     * 插入商品
     * Insert product
     */
    @Insert("INSERT INTO ecommerce_product (name, description, price, stock, status, category_id, created_at, updated_at) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock}, #{status}, #{categoryId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    /**
     * 更新商品
     * Update product
     */
    @Update("UPDATE ecommerce_product SET name = #{name}, description = #{description}, price = #{price}, " +
            "stock = #{stock}, status = #{status}, category_id = #{categoryId}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(Product product);

    /**
     * 根据 ID 删除商品
     * Delete product by ID
     */
    @Delete("DELETE FROM ecommerce_product WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询商品
     * Find product by ID
     */
    @Select("SELECT * FROM ecommerce_product WHERE id = #{id}")
    @ResultMap("productResultMap")
    Product findById(Long id);

    /**
     * 查询所有商品
     * Find all products
     */
    @Select("SELECT * FROM ecommerce_product ORDER BY created_at DESC")
    @ResultMap("productResultMap")
    List<Product> findAll();

    /**
     * 根据分类 ID 查询商品列表
     * Find products by category ID
     */
    @Select("SELECT * FROM ecommerce_product WHERE category_id = #{categoryId} ORDER BY created_at DESC")
    @ResultMap("productResultMap")
    List<Product> findByCategoryId(Long categoryId);

    /**
     * 根据状态查询商品列表
     * Find products by status
     */
    @Select("SELECT * FROM ecommerce_product WHERE status = #{status} ORDER BY created_at DESC")
    @ResultMap("productResultMap")
    List<Product> findByStatus(String status);

    /**
     * 根据名称模糊查询商品
     * Find products by name containing
     */
    @Select("SELECT * FROM ecommerce_product WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY created_at DESC")
    @ResultMap("productResultMap")
    List<Product> findByNameContaining(String name);

    /**
     * 检查库存是否足够
     * Check if stock is sufficient
     */
    @Select("SELECT * FROM ecommerce_product WHERE id = #{productId} AND stock >= #{quantity}")
    @ResultMap("productResultMap")
    Product checkStockAvailable(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 扣减库存
     * Deduct stock
     */
    @Update("UPDATE ecommerce_product SET stock = stock - #{quantity}, updated_at = NOW() " +
            "WHERE id = #{productId} AND stock >= #{quantity}")
    int deductStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    /**
     * 恢复库存
     * Restore stock
     */
    @Update("UPDATE ecommerce_product SET stock = stock + #{quantity}, updated_at = NOW() " +
            "WHERE id = #{productId}")
    int restoreStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
