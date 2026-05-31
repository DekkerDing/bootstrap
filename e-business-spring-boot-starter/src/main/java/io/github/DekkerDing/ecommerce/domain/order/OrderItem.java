package io.github.DekkerDing.ecommerce.domain.order;

import java.math.BigDecimal;

/**
 * 订单项领域模型
 * Order item domain model
 */
public class OrderItem {

    /**
     * 订单项唯一标识
     * Unique order item identifier
     */
    private Long id;

    /**
     * 所属订单 ID
     * Parent order ID
     */
    private Long orderId;

    /**
     * 商品 ID
     * Product ID
     */
    private Long productId;

    /**
     * 商品名称（快照）
     * Product name (snapshot)
     */
    private String productName;

    /**
     * 商品价格（快照）
     * Product price (snapshot)
     */
    private BigDecimal productPrice;

    /**
     * 订购数量
     * Order quantity
     */
    private Integer quantity;

    /**
     * 小计金额
     * Subtotal amount
     */
    private BigDecimal subtotal;

    public OrderItem() {
    }

    /**
     * 创建订单项
     * Create order item
     *
     * @param orderId 订单 ID / order ID
     * @param productId 商品 ID / product ID
     * @param productName 商品名称 / product name
     * @param productPrice 商品价格 / product price
     * @param quantity 数量 / quantity
     */
    public OrderItem(Long orderId, Long productId, String productName, BigDecimal productPrice, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.subtotal = productPrice.multiply(BigDecimal.valueOf(quantity));
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
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
