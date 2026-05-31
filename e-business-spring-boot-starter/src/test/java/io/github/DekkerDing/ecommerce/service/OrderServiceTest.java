package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.domain.order.OrderItem;
import io.github.DekkerDing.ecommerce.repository.OrderRepository;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 订单服务单元测试
 * Order Service Unit Tests
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, productRepository);
    }

    @Test
    void testCreateOrder_Success() {
        // 准备测试数据 / Prepare test data
        Order order = new Order();
        order.setOrderNumber("ORD001");
        order.setUserId(1L);
        order.setStatus("PENDING");

        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setProductName("测试商品 / Test Product");
        item.setProductPrice(new BigDecimal("100.00"));
        item.setQuantity(2);
        item.setSubtotal(new BigDecimal("200.00"));

        order.setItems(Collections.singletonList(item));

        when(productRepository.checkStockAvailable(any(), any())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(order);

        // 执行测试 / Execute test
        Order result = orderService.createOrder(order);

        // 验证结果 / Verify results
        assertNotNull(result);
        assertEquals(new BigDecimal("200.00"), result.getTotalAmount());
        verify(productRepository, times(1)).deductStock(any(), any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testCreateOrder_InsufficientStock() {
        // 测试库存不足的情况 / Test insufficient stock
        Order order = new Order();
        order.setOrderNumber("ORD002");

        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(100);

        order.setItems(Collections.singletonList(item));

        when(productRepository.checkStockAvailable(any(), any())).thenReturn(false);

        // 执行测试并验证异常 / Execute test and verify exception
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
        verify(productRepository, never()).deductStock(any(), any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testCancelOrder_Success() {
        // 测试取消订单 / Test cancel order
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");

        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(2);
        order.setItems(Collections.singletonList(item));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.canTransitionStatus(any(), any())).thenReturn(true);

        // 执行测试 / Execute test
        Order result = orderService.cancelOrder(orderId);

        // 验证结果 / Verify results
        assertEquals("CANCELLED", result.getStatus());
        verify(productRepository, times(1)).restoreStock(any(), any());
    }

    @Test
    void testCalculateOrderTotal() {
        // 测试计算订单总金额 / Test calculate order total
        Order order = new Order();

        OrderItem item1 = new OrderItem();
        item1.setProductPrice(new BigDecimal("50.00"));
        item1.setQuantity(2);

        OrderItem item2 = new OrderItem();
        item2.setProductPrice(new BigDecimal("30.00"));
        item2.setQuantity(1);

        order.setItems(Arrays.asList(item1, item2));

        // 执行测试 / Execute test
        BigDecimal total = orderService.calculateOrderTotal(order);

        // 验证结果 / Verify results
        assertEquals(new BigDecimal("130.00"), total);
    }

    @Test
    void testCanTransitionStatus_ValidTransition() {
        // 测试有效的状态转换 / Test valid status transition
        assertTrue(orderService.canTransitionOrderStatus("PENDING", "PAID"));
        assertTrue(orderService.canTransitionOrderStatus("PAID", "SHIPPED"));
        assertTrue(orderService.canTransitionOrderStatus("SHIPPED", "DELIVERED"));
    }

    @Test
    void testCanTransitionStatus_InvalidTransition() {
        // 测试无效的状态转换 / Test invalid status transition
        assertFalse(orderService.canTransitionOrderStatus("DELIVERED", "PENDING"));
        assertFalse(orderService.canTransitionOrderStatus("CANCELLED", "PAID"));
    }
}
