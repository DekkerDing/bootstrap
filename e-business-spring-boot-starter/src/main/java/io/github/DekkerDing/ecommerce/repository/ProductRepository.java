package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.product.Product;

import java.util.List;
import java.util.Optional;

/**
 * 商品仓储接口
 * Product Repository Interface
 * <p>
 * 框架无关的商品数据访问抽象
 * Framework-agnostic product data access abstraction
 * </p>
 */
public interface ProductRepository {

    /**
     * 保存商品
     * Save product
     *
     * @param product 商品 / product
     * @return 保存后的商品 / saved product
     */
    Product save(Product product);

    /**
     * 更新商品
     * Update product
     *
     * @param product 商品 / product
     * @return 更新后的商品 / updated product
     */
    Product update(Product product);

    /**
     * 根据 ID 删除商品
     * Delete product by ID
     *
     * @param id 商品 ID / product ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询商品
     * Find product by ID
     *
     * @param id 商品 ID / product ID
     * @return 商品 / product
     */
    Optional<Product> findById(Long id);

    /**
     * 查询所有商品
     * Find all products
     *
     * @return 商品列表 / product list
     */
    List<Product> findAll();

    /**
     * 根据分类 ID 查询商品列表
     * Find products by category ID
     *
     * @param categoryId 分类 ID / category ID
     * @return 商品列表 / product list
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * 根据状态查询商品列表
     * Find products by status
     *
     * @param status 状态 / status
     * @return 商品列表 / product list
     */
    List<Product> findByStatus(String status);

    /**
     * 根据名称模糊查询商品
     * Find products by name containing
     *
     * @param name 名称关键词 / name keyword
     * @return 商品列表 / product list
     */
    List<Product> findByNameContaining(String name);

    /**
     * 检查库存是否足够
     * Check if stock is sufficient
     *
     * @param productId 商品 ID / product ID
     * @param quantity  数量 / quantity
     * @return 是否库存充足 / whether stock is sufficient
     */
    boolean checkStockAvailable(Long productId, Integer quantity);

    /**
     * 扣减库存
     * Deduct stock
     *
     * @param productId 商品 ID / product ID
     * @param quantity  数量 / quantity
     */
    void deductStock(Long productId, Integer quantity);

    /**
     * 恢复库存
     * Restore stock
     *
     * @param productId 商品 ID / product ID
     * @param quantity  数量 / quantity
     */
    void restoreStock(Long productId, Integer quantity);
}
