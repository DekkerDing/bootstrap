package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import io.github.DekkerDing.ecommerce.repository.PaymentRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.PaymentMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 支付 MyBatis 仓储实现
 * Payment MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class PaymentRepositoryMybatisImpl implements PaymentRepository {

    private final PaymentMybatisMapper mapper;

    @Autowired
    public PaymentRepositoryMybatisImpl(PaymentMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Payment save(Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        mapper.insert(payment);
        return payment;
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        payment.setUpdatedAt(LocalDateTime.now());
        mapper.update(payment);
        return payment;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        Payment payment = mapper.findById(id);
        return Optional.ofNullable(payment);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        Payment payment = mapper.findByOrderId(orderId);
        return Optional.ofNullable(payment);
    }

    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        Payment payment = mapper.findByTransactionId(transactionId);
        return Optional.ofNullable(payment);
    }

    @Override
    public List<Payment> findByStatus(String status) {
        return mapper.findByStatus(status);
    }

    @Override
    @Transactional
    public void updateStatus(Long paymentId, String status) {
        mapper.updateStatus(paymentId, status);
    }

    @Override
    @Transactional
    public void saveCallbackData(Long paymentId, String callbackData) {
        mapper.saveCallbackData(paymentId, callbackData);
    }
}
