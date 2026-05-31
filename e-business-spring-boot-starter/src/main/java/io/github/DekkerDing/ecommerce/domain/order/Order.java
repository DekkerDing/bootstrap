package io.github.DekkerDing.ecommerce.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 订单领域模型
 * Order domain model
 */
public class Order {

    /**
     * 订单唯一标识
     * Unique order identifier
     */
    private Long id;

    /**
     * 订单号（人类可读）
     * Order number (human-readable)
     */
    private String orderNumber;

    /**
     * 用户 ID
     * User ID
     */
    private Long userId;

    /**
     * 订单状态：PENDING-待支付，PAID-已支付，SHIPPED-已发货，COMPLETED-已完成，CANCELLED-已取消
     * Order status: PENDING-pending payment, PAID-paid, SHIPPED-shipped, COMPLETED-completed, CANCELLED-cancelled
     */
    private String status;

    /**
     * 订单总金额
     * Total order amount
     */
    private BigDecimal totalAmount;

    /**
     * 订单项列表
     * Order items list
     */
    private List<OrderItem> items;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     * Last update timestamp
     */
    private LocalDateTime updatedAt;

    public Order() {
        this.orderNumber = generateOrderNumber();
        this.status = "PENDING";
        this.totalAmount = BigDecimal.ZERO;
        this.items = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 生成订单号
     * Generate order number
     *
     * @return 订单号 / order number
     */
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        this.updatedAt = LocalDateTime.now();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 添加订单项
     * Add order item
     *
     * @param item 订单项 / order item
     */
    public void addItem(OrderItem item) {
        this.items.add(item);
        recalculateTotal();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 重新计算订单总金额
     * Recalculate total order amount
     */
    public void recalculateTotal() {
        BigDecimal total = this.items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalAmount = total;
    }

    /**
     * 检查订单状态是否可以转换
     * Check if order status can be transitioned
     *
     * @param newStatus 新状态 / new status
     * @return 是否可以转换 / whether transition is allowed
     */
    public boolean canTransitionTo(String newStatus) {
        if (this.status.equals(newStatus)) {
            return false;
        }

        // 定义允许的状态转换
        // Define allowed status transitions
        switch (this.status) {
            case "PENDING":
                return "PAID".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "PAID":
                return "SHIPPED".equals(newStatus);
            case "SHIPPED":
                return "COMPLETED".equals(newStatus);
            case "COMPLETED":
            case "CANCELLED":
                return false;
            default:
                return false;
        }
    }

    /**
     * 检查是否可以取消订单
     * Check if order can be cancelled
     *
     * @return 是否可以取消 / whether cancellable
     */
    public boolean isCancellable() {
        return "PENDING".equals(this.status);
    }
}
