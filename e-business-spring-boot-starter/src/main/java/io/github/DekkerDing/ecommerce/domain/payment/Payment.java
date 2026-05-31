package io.github.DekkerDing.ecommerce.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付领域模型
 * Payment domain model
 */
public class Payment {

    /**
     * 支付唯一标识
     * Unique payment identifier
     */
    private Long id;

    /**
     * 关联订单 ID
     * Associated order ID
     */
    private Long orderId;

    /**
     * 外部交易 ID（来自支付平台）
     * External transaction ID (from payment platform)
     */
    private String transactionId;

    /**
     * 支付渠道：ALIPAY-支付宝，WECHAT-微信，CREDIT_CARD-信用卡
     * Payment channel: ALIPAY-Alipay, WECHAT-WeChat Pay, CREDIT_CARD-Credit Card
     */
    private String channel;

    /**
     * 支付金额
     * Payment amount
     */
    private BigDecimal amount;

    /**
     * 支付状态：PENDING-待支付，SUCCESS-成功，FAILED-失败，REFUNDED-已退款
     * Payment status: PENDING-pending, SUCCESS-success, FAILED-failed, REFUNDED-refunded
     */
    private String status;

    /**
     * 回调数据（原始数据）
     * Callback data (raw data)
     */
    private String callbackData;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     * Last update timestamp
     */
    private LocalDateTime updatedAt;

    public Payment() {
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 检查支付是否成功
     * Check if payment is successful
     *
     * @return 是否成功 / whether successful
     */
    public boolean isSuccessful() {
        return "SUCCESS".equals(this.status);
    }

    /**
     * 检查支付是否失败
     * Check if payment is failed
     *
     * @return 是否失败 / whether failed
     */
    public boolean isFailed() {
        return "FAILED".equals(this.status);
    }
}
