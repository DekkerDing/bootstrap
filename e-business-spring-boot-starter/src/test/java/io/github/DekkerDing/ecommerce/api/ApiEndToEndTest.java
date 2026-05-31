package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.product.Category;
import io.github.DekkerDing.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * API 端到端测试
 * API End-to-End Tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiEndToEndTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // 准备测试数据 / Prepare test data
        testCategory = new Category();
        testCategory.setName("测试分类 / Test Category");

        testProduct = new Product();
        testProduct.setName("测试商品 / Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStock(100);
        testProduct.setStatus("ACTIVE");
    }

    @Test
    void testCreateProduct() {
        // 测试创建商品 API / Test create product API
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/products",
                testProduct,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getCode());
    }

    @Test
    void testGetAllProducts() {
        // 测试获取所有商品 API / Test get all products API
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                "/api/products",
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetProductById() {
        // 测试根据 ID 获取商品 API / Test get product by ID API
        // 首先创建一个商品 / First create a product
        Product created = restTemplate.postForObject(
                "/api/products",
                testProduct,
                Product.class
        );

        // 查询商品 / Query product
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                "/api/products/" + created.getId(),
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateStock() {
        // 测试更新库存 API / Test update stock API
        // 首先创建一个商品 / First create a product
        Product created = restTemplate.postForObject(
                "/api/products",
                testProduct,
                Product.class
        );

        // 更新库存 / Update stock
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                "/api/products/" + created.getId() + "/stock?quantity=200",
                HttpMethod.PATCH,
                null,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
