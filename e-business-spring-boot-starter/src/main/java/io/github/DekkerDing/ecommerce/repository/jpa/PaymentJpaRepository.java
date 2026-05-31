package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 支付 JPA 仓储接口
 * Payment JPA Repository Interface
 */
@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

    /**
     * 根据订单 ID 查询支付信息
     * Find payment by order ID
     */
    Optional<PaymentEntity> findByOrderId(Long orderId);

    /**
     * 根据交易 ID 查询支付信息
     * Find payment by transaction ID
     */
    Optional<PaymentEntity> findByTransactionId(String transactionId);

    /**
     * 根据状态查询支付列表
     * Find payments by status
     */
    List<PaymentEntity> findByStatus(String status);

    /**
     * 根据订单 ID 和状态查询支付信息
     * Find payment by order ID and status
     */
    Optional<PaymentEntity> findByOrderIdAndStatus(Long orderId, String status);
}
