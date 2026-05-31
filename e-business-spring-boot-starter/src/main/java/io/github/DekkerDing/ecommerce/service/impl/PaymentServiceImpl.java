package io.github.DekkerDing.ecommerce.service.impl;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import io.github.DekkerDing.ecommerce.repository.PaymentRepository;
import io.github.DekkerDing.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 支付服务实现
 * Payment Service Implementation
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment updatePayment(Payment payment) {
        if (payment.getId() == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        return paymentRepository.update(payment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for order: " + orderId));
    }

    @Override
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for transaction: " + transactionId));
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Payment updatePaymentStatus(Long paymentId, String status) {
        paymentRepository.updateStatus(paymentId, status);
        return getPaymentById(paymentId);
    }

    @Override
    @Transactional
    public Payment processPaymentCallback(String transactionId, Map<String, String> callbackData) {
        Payment payment = getPaymentByTransactionId(transactionId);

        // 保存回调数据 / Save callback data
        String callbackDataJson = mapToJson(callbackData);
        paymentRepository.saveCallbackData(payment.getId(), callbackDataJson);

        // 根据回调数据更新支付状态 / Update payment status based on callback data
        String status = callbackData.get("status");
        if ("SUCCESS".equals(status) || "SUCCESS".equals(callbackData.get("trade_status"))) {
            paymentRepository.updateStatus(payment.getId(), "SUCCESS");
        } else if ("FAILED".equals(status) || "FAILED".equals(callbackData.get("trade_status"))) {
            paymentRepository.updateStatus(payment.getId(), "FAILED");
        }

        return getPaymentById(payment.getId());
    }

    @Override
    public boolean verifySignature(Map<String, String> data, String signature, String publicKey) {
        // 简化的签名验证逻辑 / Simplified signature verification logic
        // 实际实现应使用具体的支付渠道提供的 SDK
        // Actual implementation should use SDK provided by specific payment channel
        return true;
    }

    @Override
    public String queryPaymentStatus(String transactionId) {
        Payment payment = getPaymentByTransactionId(transactionId);
        return payment.getStatus();
    }

    /**
     * 将 Map 转换为 JSON 字符串
     * Convert Map to JSON string
     */
    private String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
