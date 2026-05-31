package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;

import java.util.List;
import java.util.Optional;

/**
 * 支付仓储接口
 * Payment Repository Interface
 * <p>
 * 框架无关的支付数据访问抽象
 * Framework-agnostic payment data access abstraction
 * </p>
 */
public interface PaymentRepository {

    /**
     * 保存支付信息
     * Save payment
     *
     * @param payment 支付信息 / payment
     * @return 保存后的支付信息 / saved payment
     */
    Payment save(Payment payment);

    /**
     * 更新支付信息
     * Update payment
     *
     * @param payment 支付信息 / payment
     * @return 更新后的支付信息 / updated payment
     */
    Payment update(Payment payment);

    /**
     * 根据 ID 删除支付信息
     * Delete payment by ID
     *
     * @param id 支付 ID / payment ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询支付信息
     * Find payment by ID
     *
     * @param id 支付 ID / payment ID
     * @return 支付信息 / payment
     */
    Optional<Payment> findById(Long id);

    /**
     * 根据订单 ID 查询支付信息
     * Find payment by order ID
     *
     * @param orderId 订单 ID / order ID
     * @return 支付信息 / payment
     */
    Optional<Payment> findByOrderId(Long orderId);

    /**
     * 根据交易 ID 查询支付信息
     * Find payment by transaction ID
     *
     * @param transactionId 交易 ID / transaction ID
     * @return 支付信息 / payment
     */
    Optional<Payment> findByTransactionId(String transactionId);

    /**
     * 根据状态查询支付列表
     * Find payments by status
     *
     * @param status 状态 / status
     * @return 支付列表 / payment list
     */
    List<Payment> findByStatus(String status);

    /**
     * 更新支付状态
     * Update payment status
     *
     * @param paymentId 支付 ID / payment ID
     * @param status    新状态 / new status
     */
    void updateStatus(Long paymentId, String status);

    /**
     * 保存回调数据
     * Save callback data
     *
     * @param paymentId    支付 ID / payment ID
     * @param callbackData 回调数据 / callback data
     */
    void saveCallbackData(Long paymentId, String callbackData);
}
