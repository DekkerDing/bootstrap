package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.repository.OrderRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.OrderMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

/**
 * 订单 MyBatis 仓储实现
 * Order MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class OrderRepositoryMybatisImpl implements OrderRepository {

    private final OrderMybatisMapper mapper;

    private static final Set<String> VALID_TRANSITIONS_PENDING = new HashSet<>(java.util.Arrays.asList("PAID", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_PAID = new HashSet<>(java.util.Arrays.asList("SHIPPED", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_SHIPPED = new HashSet<>(java.util.Arrays.asList("DELIVERED", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_DELIVERED = new HashSet<>();

    @Autowired
    public OrderRepositoryMybatisImpl(OrderMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        mapper.insert(order);
        return order;
    }

    @Override
    @Transactional
    public Order update(Order order) {
        order.setUpdatedAt(LocalDateTime.now());
        mapper.update(order);
        return order;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = mapper.findById(id);
        return Optional.ofNullable(order);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        Order order = mapper.findByOrderNumber(orderNumber);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public List<Order> findByUserIdAndStatus(Long userId, String status) {
        return mapper.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Order> findByStatus(String status) {
        return mapper.findByStatus(status);
    }

    @Override
    @Transactional
    public void updateStatus(Long orderId, String newStatus) {
        Order order = findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (!canTransitionStatus(order.getStatus(), newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from " + order.getStatus() + " to " + newStatus);
        }
        mapper.updateStatus(orderId, newStatus);
    }

    @Override
    public boolean canTransitionStatus(String currentStatus, String newStatus) {
        if (currentStatus.equals(newStatus)) {
            return false;
        }
        switch (currentStatus) {
            case "PENDING":
                return VALID_TRANSITIONS_PENDING.contains(newStatus);
            case "PAID":
                return VALID_TRANSITIONS_PAID.contains(newStatus);
            case "SHIPPED":
                return VALID_TRANSITIONS_SHIPPED.contains(newStatus);
            case "DELIVERED":
                return VALID_TRANSITIONS_DELIVERED.contains(newStatus);
            default:
                return false;
        }
    }
}
