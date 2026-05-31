package io.github.DekkerDing.ecommerce.service.impl;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.repository.OrderRepository;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单服务实现
 * Order Service Implementation
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        // 验证商品库存 / Validate product stock
        for (io.github.DekkerDing.ecommerce.domain.order.OrderItem item : order.getItems()) {
            if (!productRepository.checkStockAvailable(item.getProductId(), item.getQuantity())) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProductId());
            }
        }

        // 计算订单总金额 / Calculate order total
        BigDecimal totalAmount = calculateOrderTotal(order);
        order.setTotalAmount(totalAmount);

        // 扣减库存 / Deduct stock
        for (io.github.DekkerDing.ecommerce.domain.order.OrderItem item : order.getItems()) {
            productRepository.deductStock(item.getProductId(), item.getQuantity());
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderRepository.update(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderNumber));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByUserIdAndStatus(Long userId, String status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = getOrderById(orderId);
        if (!canTransitionOrderStatus(order.getStatus(), newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from " + order.getStatus() + " to " + newStatus);
        }
        orderRepository.updateStatus(orderId, newStatus);
        order.setStatus(newStatus);
        return order;
    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if ("PENDING".equals(order.getStatus()) || "PAID".equals(order.getStatus())) {
            // 恢复库存 / Restore stock
            for (io.github.DekkerDing.ecommerce.domain.order.OrderItem item : order.getItems()) {
                productRepository.restoreStock(item.getProductId(), item.getQuantity());
            }
            return updateOrderStatus(orderId, "CANCELLED");
        }
        throw new IllegalArgumentException("Order cannot be cancelled in current status: " + order.getStatus());
    }

    @Override
    public BigDecimal calculateOrderTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (io.github.DekkerDing.ecommerce.domain.order.OrderItem item : order.getItems()) {
            BigDecimal itemTotal = item.getProductPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }

    @Override
    public boolean canTransitionOrderStatus(String currentStatus, String newStatus) {
        return orderRepository.canTransitionStatus(currentStatus, newStatus);
    }
}
