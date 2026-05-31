package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import io.github.DekkerDing.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 支付控制器
 * Payment Controller
 */
@RestController
@RequestMapping("/api/payments")
@ConditionalOnProperty(prefix = "e-commerce.modules", name = "payment-enabled", havingValue = "true", matchIfMissing = true)
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 创建支付
     * Create payment
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> createPayment(@Valid @RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 根据 ID 查询支付
     * Get payment by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 根据订单 ID 查询支付
     * Get payment by order ID
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByOrderId(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 根据交易 ID 查询支付
     * Get payment by transaction ID
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByTransactionId(@PathVariable String transactionId) {
        Payment payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 根据状态查询支付列表
     * Get payments by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Payment>>> getPaymentsByStatus(@PathVariable String status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(payments));
    }

    /**
     * 更新支付状态
     * Update payment status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Payment>> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        Payment payment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 处理支付回调
     * Process payment callback
     */
    @PostMapping("/callback")
    public ResponseEntity<ApiResponse<Payment>> processPaymentCallback(@RequestParam String transactionId, @RequestBody Map<String, String> callbackData) {
        Payment payment = paymentService.processPaymentCallback(transactionId, callbackData);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 查询支付状态
     * Query payment status
     */
    @GetMapping("/query/{transactionId}")
    public ResponseEntity<ApiResponse<String>> queryPaymentStatus(@PathVariable String transactionId) {
        String status = paymentService.queryPaymentStatus(transactionId);
        return ResponseEntity.ok(ApiResponse.success(status));
    }
}
