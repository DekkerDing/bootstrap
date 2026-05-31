package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.order.Order;

import java.util.List;
import java.util.Optional;

/**
 * 订单仓储接口
 * Order Repository Interface
 * <p>
 * 框架无关的订单数据访问抽象
 * Framework-agnostic order data access abstraction
 * </p>
 */
public interface OrderRepository {

    /**
     * 保存订单
     * Save order
     *
     * @param order 订单 / order
     * @return 保存后的订单 / saved order
     */
    Order save(Order order);

    /**
     * 更新订单
     * Update order
     *
     * @param order 订单 / order
     * @return 更新后的订单 / updated order
     */
    Order update(Order order);

    /**
     * 根据 ID 删除订单
     * Delete order by ID
     *
     * @param id 订单 ID / order ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询订单
     * Find order by ID
     *
     * @param id 订单 ID / order ID
     * @return 订单 / order
     */
    Optional<Order> findById(Long id);

    /**
     * 根据订单号查询订单
     * Find order by order number
     *
     * @param orderNumber 订单号 / order number
     * @return 订单 / order
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * 根据用户 ID 查询订单列表
     * Find orders by user ID
     *
     * @param userId 用户 ID / user ID
     * @return 订单列表 / order list
     */
    List<Order> findByUserId(Long userId);

    /**
     * 根据用户 ID 和状态查询订单列表
     * Find orders by user ID and status
     *
     * @param userId 用户 ID / user ID
     * @param status  状态 / status
     * @return 订单列表 / order list
     */
    List<Order> findByUserIdAndStatus(Long userId, String status);

    /**
     * 根据状态查询订单列表
     * Find orders by status
     *
     * @param status 状态 / status
     * @return 订单列表 / order list
     */
    List<Order> findByStatus(String status);

    /**
     * 更新订单状态
     * Update order status
     *
     * @param orderId     订单 ID / order ID
     * @param newStatus   新状态 / new status
     */
    void updateStatus(Long orderId, String newStatus);

    /**
     * 检查订单状态是否可以转换
     * Check if order status can be transitioned
     *
     * @param currentStatus 当前状态 / current status
     * @param newStatus     新状态 / new status
     * @return 是否可以转换 / whether can transition
     */
    boolean canTransitionStatus(String currentStatus, String newStatus);
}
