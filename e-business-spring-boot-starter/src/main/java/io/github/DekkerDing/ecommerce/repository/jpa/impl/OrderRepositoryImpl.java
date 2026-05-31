package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.repository.OrderRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.OrderJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.OrderEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

/**
 * 订单 JPA 仓储实现
 * Order JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    private static final Set<String> VALID_TRANSITIONS_PENDING = new HashSet<>(java.util.Arrays.asList("PAID", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_PAID = new HashSet<>(java.util.Arrays.asList("SHIPPED", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_SHIPPED = new HashSet<>(java.util.Arrays.asList("DELIVERED", "CANCELLED"));
    private static final Set<String> VALID_TRANSITIONS_DELIVERED = new HashSet<>();

    @Autowired
    public OrderRepositoryImpl(OrderJpaRepository jpaRepository, OrderMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        entity.setId(order.getId());
        OrderEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return jpaRepository.findByOrderNumber(orderNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return mapper.toDomainList(jpaRepository.findByUserId(userId));
    }

    @Override
    public List<Order> findByUserIdAndStatus(Long userId, String status) {
        return mapper.toDomainList(jpaRepository.findByUserIdAndStatus(userId, status));
    }

    @Override
    public List<Order> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    @Transactional
    public void updateStatus(Long orderId, String newStatus) {
        OrderEntity entity = jpaRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (!canTransitionStatus(entity.getStatus(), newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from " + entity.getStatus() + " to " + newStatus);
        }
        entity.setStatus(newStatus);
        jpaRepository.save(entity);
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
