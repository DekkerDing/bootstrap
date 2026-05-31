package io.github.DekkerDing.ecommerce.service.impl;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.CategoryRepository;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务实现
 * Product Service Implementation
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (product.getCategoryId() != null) {
            Category category = categoryRepository.findById(product.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + product.getCategoryId()));
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.update(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    @Override
    @Transactional
    public Product updateStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        product.setStock(quantity);
        return productRepository.update(product);
    }

    @Override
    public boolean checkStock(Long productId, Integer quantity) {
        return productRepository.checkStockAvailable(productId, quantity);
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        return categoryRepository.update(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (categoryRepository.hasChildren(id)) {
            throw new IllegalArgumentException("Cannot delete category with subcategories");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getSubcategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findTopLevelCategories();
    }
}
