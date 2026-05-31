package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import io.github.DekkerDing.ecommerce.repository.PaymentRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.PaymentJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.PaymentEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 支付 JPA 仓储实现
 * Payment JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper mapper;

    @Autowired
    public PaymentRepositoryImpl(PaymentJpaRepository jpaRepository, PaymentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Payment save(Payment payment) {
        PaymentEntity entity = mapper.toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        PaymentEntity entity = mapper.toEntity(payment);
        entity.setId(payment.getId());
        PaymentEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return jpaRepository.findByOrderId(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        return jpaRepository.findByTransactionId(transactionId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Payment> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    @Transactional
    public void updateStatus(Long paymentId, String status) {
        PaymentEntity entity = jpaRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        entity.setStatus(status);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void saveCallbackData(Long paymentId, String callbackData) {
        PaymentEntity entity = jpaRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        entity.setCallbackData(callbackData);
        jpaRepository.save(entity);
    }
}
