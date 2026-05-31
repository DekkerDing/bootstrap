package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.order.Order;

import java.util.List;

/**
 * 订单服务接口
 * Order Service Interface
 */
public interface OrderService {

    /**
     * 创建订单
     * Create order
     */
    Order createOrder(Order order);

    /**
     * 更新订单
     * Update order
     */
    Order updateOrder(Order order);

    /**
     * 根据 ID 删除订单
     * Delete order by ID
     */
    void deleteOrder(Long id);

    /**
     * 根据 ID 查询订单
     * Find order by ID
     */
    Order getOrderById(Long id);

    /**
     * 根据订单号查询订单
     * Find order by order number
     */
    Order getOrderByOrderNumber(String orderNumber);

    /**
     * 根据用户 ID 查询订单列表
     * Find orders by user ID
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * 根据用户 ID 和状态查询订单列表
     * Find orders by user ID and status
     */
    List<Order> getOrdersByUserIdAndStatus(Long userId, String status);

    /**
     * 根据状态查询订单列表
     * Find orders by status
     */
    List<Order> getOrdersByStatus(String status);

    /**
     * 更新订单状态
     * Update order status
     */
    Order updateOrderStatus(Long orderId, String newStatus);

    /**
     * 取消订单
     * Cancel order
     */
    Order cancelOrder(Long orderId);

    /**
     * 计算订单总金额
     * Calculate order total amount
     */
    java.math.BigDecimal calculateOrderTotal(Order order);

    /**
     * 验证订单状态转换
     * Validate order status transition
     */
    boolean canTransitionOrderStatus(String currentStatus, String newStatus);
}
