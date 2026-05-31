package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.domain.product.Product;
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

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // 准备测试数据 / Prepare test data
        testProduct = new Product();
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
        Product saved = productJpaRepository.save(testProduct);

        assertNotNull(saved.getId());
        assertEquals("测试商品 / Test Product", saved.getName());
    }

    @Test
    void testFindById() {
        // 测试根据 ID 查询 / Test find by ID
        Product saved = productJpaRepository.save(testProduct);

        Optional<Product> found = productJpaRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    void testFindByStatus() {
        // 测试根据状态查询 / Test find by status
        productJpaRepository.save(testProduct);

        List<Product> activeProducts = productJpaRepository.findByStatus("ACTIVE");

        assertFalse(activeProducts.isEmpty());
        assertTrue(activeProducts.stream().allMatch(p -> "ACTIVE".equals(p.getStatus())));
    }

    @Test
    void testFindByCategoryId() {
        // 测试根据分类 ID 查询 / Test find by category ID
        productJpaRepository.save(testProduct);

        List<Product> categoryProducts = productJpaRepository.findByCategoryId(1L);

        assertFalse(categoryProducts.isEmpty());
        assertTrue(categoryProducts.stream().allMatch(p -> 1L.equals(p.getCategoryId())));
    }

    @Test
    void testCheckStockAvailable() {
        // 测试检查库存可用 / Test check stock available
        Product saved = productJpaRepository.save(testProduct);

        Optional<Product> available = productJpaRepository.checkStockAvailable(saved.getId(), 50);

        assertTrue(available.isPresent());
    }

    @Test
    void testUpdateStock() {
        // 测试更新库存 / Test update stock
        Product saved = productJpaRepository.save(testProduct);

        // 创建新的商品实体并更新库存 / Create new entity and update stock
        ProductEntity entity = new ProductEntity();
        entity.setId(saved.getId());
        entity.setName(saved.getName());
        entity.setDescription(saved.getDescription());
        entity.setPrice(saved.getPrice());
        entity.setStock(150);
        entity.setStatus(saved.getStatus());
        entity.setCategoryId(saved.getCategoryId());

        productJpaRepository.save(entity);

        // 重新查询验证 / Verify by re-querying
        // 注意：这里需要使用 Mapper 转换或直接查询 Entity
        // Note: Need to use Mapper for conversion or query Entity directly
    }

    @Test
    void testDeleteProduct() {
        // 测试删除商品 / Test delete product
        Product saved = productJpaRepository.save(testProduct);
        Long productId = saved.getId();

        productJpaRepository.deleteById(productId);

        Optional<Product> deleted = productJpaRepository.findById(productId);
        assertFalse(deleted.isPresent());
    }
}
