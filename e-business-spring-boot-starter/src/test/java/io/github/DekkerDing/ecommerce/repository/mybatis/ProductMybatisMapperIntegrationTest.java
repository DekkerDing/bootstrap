package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.mybatis.ProductMybatisMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 商品 MyBatis Mapper 集成测试
 * Product MyBatis Mapper Integration Tests
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductMybatisMapperIntegrationTest {

    @Autowired
    private ProductMybatisMapper productMybatisMapper;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setName("测试商品 / Test Product");
        testProduct.setDescription("MyBatis 测试商品 / MyBatis test product");
        testProduct.setPrice(new BigDecimal("88.88"));
        testProduct.setStock(50);
        testProduct.setStatus("AVAILABLE");
        testProduct.setCategoryId(1L);
    }

    @Test
    void testInsert() {
        // 测试插入商品 / Test insert product
        int result = productMybatisMapper.insert(testProduct);

        assertEquals(1, result);
        assertNotNull(testProduct.getId());
    }

    @Test
    void testFindById() {
        // 测试根据 ID 查询 / Test find by ID
        productMybatisMapper.insert(testProduct);

        Product found = productMybatisMapper.findById(testProduct.getId());

        assertNotNull(found);
        assertEquals(testProduct.getName(), found.getName());
    }

    @Test
    void testFindByStatus() {
        // 测试根据状态查询 / Test find by status
        productMybatisMapper.insert(testProduct);

        List<Product> products = productMybatisMapper.findByStatus("AVAILABLE");

        assertFalse(products.isEmpty());
    }

    @Test
    void testCheckStockAvailable() {
        // 测试检查库存 / Test check stock available
        productMybatisMapper.insert(testProduct);

        Product available = productMybatisMapper.checkStockAvailable(testProduct.getId(), 25);

        assertNotNull(available);
    }

    @Test
    void testUpdate() {
        // 测试更新商品 / Test update product
        productMybatisMapper.insert(testProduct);

        testProduct.setStock(200);
        testProduct.setPrice(new BigDecimal("99.99"));

        int result = productMybatisMapper.update(testProduct);

        assertEquals(1, result);
    }

    @Test
    void testDeductStock() {
        // 测试扣减库存 / Test deduct stock
        productMybatisMapper.insert(testProduct);

        int result = productMybatisMapper.deductStock(testProduct.getId(), 10);

        assertEquals(1, result);
    }

    @Test
    void testRestoreStock() {
        // 测试恢复库存 / Test restore stock
        productMybatisMapper.insert(testProduct);

        int result = productMybatisMapper.restoreStock(testProduct.getId(), 10);

        assertEquals(1, result);
    }

    @Test
    void testDeleteById() {
        // 测试删除商品 / Test delete product
        productMybatisMapper.insert(testProduct);
        Long productId = testProduct.getId();

        productMybatisMapper.deleteById(productId);

        Product deleted = productMybatisMapper.findById(productId);
        assertNull(deleted);
    }
}
