package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品控制器
 * Product Controller
 */
@RestController
@RequestMapping("/api/products")
@ConditionalOnProperty(prefix = "e-commerce.modules", name = "product-enabled", havingValue = "true", matchIfMissing = true)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 创建商品
     * Create product
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 更新商品
     * Update product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        product.setId(id);
        Product updated = productService.updateProduct(product);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除商品
     * Delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 根据 ID 查询商品
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    /**
     * 查询所有商品
     * Get all products
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    /**
     * 根据分类 ID 查询商品
     * Get products by category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    /**
     * 根据状态查询商品
     * Get products by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByStatus(@PathVariable String status) {
        List<Product> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    /**
     * 搜索商品
     * Search products
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    /**
     * 更新库存
     * Update stock
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Product>> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        Product product = productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    /**
     * 检查库存
     * Check stock
     */
    @GetMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Boolean>> checkStock(@PathVariable Long id, @RequestParam Integer quantity) {
        boolean available = productService.checkStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success(available));
    }

    // ========== 分类管理 / Category Management ==========

    /**
     * 创建分类
     * Create category
     */
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category) {
        Category created = productService.createCategory(category);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 更新分类
     * Update category
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        category.setId(id);
        Category updated = productService.updateCategory(category);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除分类
     * Delete category
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        productService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 根据 ID 查询分类
     * Get category by ID
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        Category category = productService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success(category));
    }

    /**
     * 查询所有分类
     * Get all categories
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = productService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    /**
     * 查询子分类
     * Get subcategories
     */
    @GetMapping("/categories/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<Category>>> getSubcategories(@PathVariable Long parentId) {
        List<Category> categories = productService.getSubcategories(parentId);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    /**
     * 查询顶级分类
     * Get top-level categories
     */
    @GetMapping("/categories/top-level")
    public ResponseEntity<ApiResponse<List<Category>>> getTopLevelCategories() {
        List<Category> categories = productService.getTopLevelCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
}
