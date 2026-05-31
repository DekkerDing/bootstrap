package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.domain.product.Product;

import java.util.List;

/**
 * 商品服务接口
 * Product Service Interface
 */
public interface ProductService {

    /**
     * 创建商品
     * Create product
     */
    Product createProduct(Product product);

    /**
     * 更新商品
     * Update product
     */
    Product updateProduct(Product product);

    /**
     * 根据 ID 删除商品
     * Delete product by ID
     */
    void deleteProduct(Long id);

    /**
     * 根据 ID 查询商品
     * Find product by ID
     */
    Product getProductById(Long id);

    /**
     * 查询所有商品
     * Find all products
     */
    List<Product> getAllProducts();

    /**
     * 根据分类 ID 查询商品列表
     * Find products by category ID
     */
    List<Product> getProductsByCategory(Long categoryId);

    /**
     * 根据状态查询商品列表
     * Find products by status
     */
    List<Product> getProductsByStatus(String status);

    /**
     * 搜索商品
     * Search products by name
     */
    List<Product> searchProducts(String keyword);

    /**
     * 更新商品库存
     * Update product stock
     */
    Product updateStock(Long productId, Integer quantity);

    /**
     * 检查库存是否充足
     * Check if stock is sufficient
     */
    boolean checkStock(Long productId, Integer quantity);

    /**
     * 创建分类
     * Create category
     */
    Category createCategory(Category category);

    /**
     * 更新分类
     * Update category
     */
    Category updateCategory(Category category);

    /**
     * 删除分类
     * Delete category
     */
    void deleteCategory(Long id);

    /**
     * 根据 ID 查询分类
     * Find category by ID
     */
    Category getCategoryById(Long id);

    /**
     * 查询所有分类
     * Find all categories
     */
    List<Category> getAllCategories();

    /**
     * 根据父级 ID 查询子分类
     * Find subcategories by parent ID
     */
    List<Category> getSubcategories(Long parentId);

    /**
     * 查询顶级分类
     * Find top-level categories
     */
    List<Category> getTopLevelCategories();
}
