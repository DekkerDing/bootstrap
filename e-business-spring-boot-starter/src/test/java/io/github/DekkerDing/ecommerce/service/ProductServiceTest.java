package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.CategoryRepository;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 商品服务单元测试
 * Product Service Unit Tests
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    void testCreateProduct_Success() {
        // 准备测试数据 / Prepare test data
        Product product = new Product();
        product.setName("测试商品 / Test Product");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(100);
        product.setStatus("ACTIVE");

        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(productRepository.save(any())).thenReturn(product);

        // 执行测试 / Execute test
        Product result = productService.createProduct(product);

        // 验证结果 / Verify results
        assertNotNull(result);
        assertEquals("测试商品 / Test Product", result.getName());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testCreateProduct_CategoryNotFound() {
        // 测试分类不存在的情况 / Test case when category not found
        Product product = new Product();
        product.setCategoryId(999L);

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        // 执行测试并验证异常 / Execute test and verify exception
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(product));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testGetAllProducts() {
        // 准备测试数据 / Prepare test data
        Product product1 = new Product();
        product1.setName("商品 1 / Product 1");
        Product product2 = new Product();
        product2.setName("商品 2 / Product 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // 执行测试 / Execute test
        List<Product> products = productService.getAllProducts();

        // 验证结果 / Verify results
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCheckStock_Available() {
        // 测试库存检查 / Test stock check
        Long productId = 1L;
        Integer quantity = 50;

        when(productRepository.checkStockAvailable(productId, quantity)).thenReturn(true);

        // 执行测试 / Execute test
        boolean available = productService.checkStock(productId, quantity);

        // 验证结果 / Verify results
        assertTrue(available);
        verify(productRepository, times(1)).checkStockAvailable(productId, quantity);
    }

    @Test
    void testGetAllCategories() {
        // 测试获取所有分类 / Test get all categories
        Category category1 = new Category();
        category1.setName("电子产品 / Electronics");
        Category category2 = new Category();
        category2.setName("服装 / Clothing");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // 执行测试 / Execute test
        List<Category> categories = productService.getAllCategories();

        // 验证结果 / Verify results
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testDeleteCategory_WithChildren() {
        // 测试删除有子分类的分类 / Test delete category with children
        Long categoryId = 1L;

        when(categoryRepository.hasChildren(categoryId)).thenReturn(true);

        // 执行测试并验证异常 / Execute test and verify exception
        assertThrows(IllegalArgumentException.class, () -> productService.deleteCategory(categoryId));
        verify(categoryRepository, never()).deleteById(any());
    }
}
