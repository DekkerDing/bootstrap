package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;

import java.util.List;
import java.util.Map;

/**
 * 支付服务接口
 * Payment Service Interface
 */
public interface PaymentService {

    /**
     * 创建支付
     * Create payment
     */
    Payment createPayment(Payment payment);

    /**
     * 更新支付
     * Update payment
     */
    Payment updatePayment(Payment payment);

    /**
     * 根据 ID 删除支付
     * Delete payment by ID
     */
    void deletePayment(Long id);

    /**
     * 根据 ID 查询支付
     * Find payment by ID
     */
    Payment getPaymentById(Long id);

    /**
     * 根据订单 ID 查询支付
     * Find payment by order ID
     */
    Payment getPaymentByOrderId(Long orderId);

    /**
     * 根据交易 ID 查询支付
     * Find payment by transaction ID
     */
    Payment getPaymentByTransactionId(String transactionId);

    /**
     * 根据状态查询支付列表
     * Find payments by status
     */
    List<Payment> getPaymentsByStatus(String status);

    /**
     * 更新支付状态
     * Update payment status
     */
    Payment updatePaymentStatus(Long paymentId, String status);

    /**
     * 处理支付回调
     * Process payment callback
     */
    Payment processPaymentCallback(String transactionId, Map<String, String> callbackData);

    /**
     * 验证签名
     * Verify signature
     */
    boolean verifySignature(Map<String, String> data, String signature, String publicKey);

    /**
     * 查询支付状态
     * Query payment status
     */
    String queryPaymentStatus(String transactionId);
}
