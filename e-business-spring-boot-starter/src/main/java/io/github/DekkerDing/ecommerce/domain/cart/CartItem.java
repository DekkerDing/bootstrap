package io.github.DekkerDing.ecommerce.domain.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车项领域模型
 * Cart Item Domain Model
 */
public class CartItem {

    /**
     * 购物车项唯一标识
     * Unique cart item identifier
     */
    private Long id;

    /**
     * 所属购物车 ID
     * Parent cart ID
     */
    private Long cartId;

    /**
     * 商品 ID
     * Product ID
     */
    private Long productId;

    /**
     * SKU ID
     * SKU variant ID
     */
    private Long skuId;

    /**
     * 商品名称
     * Product name
     */
    private String productName;

    /**
     * 商品图片
     * Product image
     */
    private String productImage;

    /**
     * 商品价格（加入时快照）
     * Product price (snapshot at add time)
     */
    private BigDecimal price;

    /**
     * 数量
     * Quantity
     */
    private Integer quantity;

    /**
     * 是否选中
     * Is selected for checkout
     */
    private Boolean selected = true;

    /**
     * 小计金额
     * Subtotal amount
     */
    private BigDecimal subtotal;

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

    /**
     * 商品状态 - 是否在售
     * Product on sale status
     */
    private Boolean onSale = true;

    /**
     * 商品状态 - 是否有库存
     * Product in stock status
     */
    private Boolean hasStock = true;

    /**
     * 当前库存
     * Current stock quantity
     */
    private Integer stock;

    public CartItem() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.selected = true;
    }

    /**
     * 创建购物车项
     * Create cart item
     */
    public CartItem(Long productId, Long skuId, String productName, BigDecimal price, Integer quantity) {
        this();
        this.productId = productId;
        this.skuId = skuId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        updateSubtotal();
    }

    /**
     * 设置数量
     * Set quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        updateSubtotal();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 设置价格
     * Set price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
        updateSubtotal();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 设置选中状态
     * Set selected status
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 切换选中状态
     * Toggle selected status
     */
    public void toggleSelected() {
        this.selected = !this.selected;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 更新小计
     * Update subtotal
     */
    private void updateSubtotal() {
        if (price != null && quantity != null) {
            this.subtotal = price.multiply(new BigDecimal(quantity));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    /**
     * 检查项是否有效（在售且有库存）
     * Check if item is valid (on sale and in stock)
     */
    public boolean isValid() {
        return Boolean.TRUE.equals(onSale) && Boolean.TRUE.equals(hasStock) && quantity <= (stock != null ? stock : Integer.MAX_VALUE);
    }

    /**
     * 获取不可用原因
     * Get invalid reason
     */
    public String getInvalidReason() {
        if (Boolean.FALSE.equals(onSale)) {
            return "商品已下架 / Product is off sale";
        }
        if (Boolean.FALSE.equals(hasStock) || (stock != null && quantity > stock)) {
            return "库存不足 / Insufficient stock";
        }
        return null;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
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

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
