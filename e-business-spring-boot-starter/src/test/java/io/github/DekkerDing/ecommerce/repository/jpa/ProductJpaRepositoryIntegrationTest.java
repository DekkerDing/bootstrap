package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 商品 JPA 仓储集成测试（使用 H2 内存数据库）
 * Product JPA Repository Integration Tests (using H2 in-memory database)
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductJpaRepositoryIntegrationTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private ProductEntity testProduct;

    @BeforeEach
    void setUp() {
        // 准备测试数据 / Prepare test data
        testProduct = new ProductEntity();
        testProduct.setName("测试商品 / Test Product");
        testProduct.setDescription("这是一个测试商品 / This is a test product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStock(100);
        testProduct.setStatus("ACTIVE");
        testProduct.setCategoryId(1L);
    }

    @Test
    void testSaveProduct() {
        // 测试保存商品 / Test save product
        ProductEntity saved = productJpaRepository.save(testProduct);

        assertNotNull(saved.getId());
        assertEquals("测试商品 / Test Product", saved.getName());
    }

    @Test
    void testFindById() {
        // 测试根据 ID 查询 / Test find by ID
        ProductEntity saved = productJpaRepository.save(testProduct);

        Optional<ProductEntity> found = productJpaRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    void testFindByStatus() {
        // 测试根据状态查询 / Test find by status
        productJpaRepository.save(testProduct);

        List<ProductEntity> activeProducts = productJpaRepository.findByStatus("ACTIVE");

        assertFalse(activeProducts.isEmpty());
        assertTrue(activeProducts.stream().allMatch(p -> "ACTIVE".equals(p.getStatus())));
    }

    @Test
    void testFindByCategoryId() {
        // 测试根据分类 ID 查询 / Test find by category ID
        productJpaRepository.save(testProduct);

        List<ProductEntity> categoryProducts = productJpaRepository.findByCategoryId(1L);

        assertFalse(categoryProducts.isEmpty());
        assertTrue(categoryProducts.stream().allMatch(p -> Long.valueOf(1L).equals(p.getCategoryId())));
    }

    @Test
    void testCheckStockAvailable() {
        // 测试检查库存可用 / Test check stock available
        ProductEntity saved = productJpaRepository.save(testProduct);

        Optional<ProductEntity> available = productJpaRepository.checkStockAvailable(saved.getId(), 50);

        assertTrue(available.isPresent());
    }

    @Test
    void testUpdateStock() {
        // 测试更新库存 / Test update stock
        ProductEntity saved = productJpaRepository.save(testProduct);

        // 更新库存 / Update stock
        saved.setStock(150);
        productJpaRepository.save(saved);

        // 重新查询验证 / Verify by re-querying
        Optional<ProductEntity> updated = productJpaRepository.findById(saved.getId());
        assertTrue(updated.isPresent());
        assertEquals(150, updated.get().getStock());
    }

    @Test
    void testDeleteProduct() {
        // 测试删除商品 / Test delete product
        ProductEntity saved = productJpaRepository.save(testProduct);
        Long productId = saved.getId();

        productJpaRepository.deleteById(productId);

        Optional<ProductEntity> deleted = productJpaRepository.findById(productId);
        assertFalse(deleted.isPresent());
    }
}
