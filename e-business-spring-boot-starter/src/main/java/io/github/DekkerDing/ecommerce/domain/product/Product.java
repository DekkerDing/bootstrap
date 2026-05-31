package io.github.DekkerDing.ecommerce.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品领域模型
 * Product domain model
 *
 * 框架无关的纯 POJO，用于业务逻辑层
 * Framework-agnostic POJO for business logic layer
 */
public class Product {

    /**
     * 商品唯一标识
     * Unique product identifier
     */
    private Long id;

    /**
     * 商品名称
     * Product name
     */
    private String name;

    /**
     * 商品描述
     * Product description
     */
    private String description;

    /**
     * 商品价格
     * Product price
     */
    private BigDecimal price;

    /**
     * 可用库存数量
     * Available stock quantity
     */
    private Integer stock;

    /**
     * 商品状态：ACTIVE-活动，INACTIVE-非活动
     * Product status: ACTIVE-active, INACTIVE-inactive
     */
    private String status;

    /**
     * 分类 ID
     * Category ID
     */
    private Long categoryId;

    /**
     * 商品图片 URL
     * Product image URL
     */
    private String image;

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

    public Product() {
        this.status = "ACTIVE";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
     * 检查库存是否足够
     * Check if stock is sufficient
     *
     * @param quantity 需要的数量 / required quantity
     * @return 是否库存充足 / whether stock is sufficient
     */
    public boolean hasEnoughStock(Integer quantity) {
        return this.stock != null && this.stock >= quantity;
    }

    /**
     * 检查商品是否活动
     * Check if product is active
     *
     * @return 是否活动 / whether active
     */
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

    /**
     * 扣减库存
     * Decrease stock
     *
     * @param quantity 扣减数量 / quantity to decrease
     * @return 是否成功 / whether successful
     */
    public boolean decreaseStock(Integer quantity) {
        if (!hasEnoughStock(quantity)) {
            return false;
        }
        this.stock -= quantity;
        this.updatedAt = LocalDateTime.now();
        return true;
    }

    /**
     * 增加库存
     * Increase stock
     *
     * @param quantity 增加数量 / quantity to increase
     */
    public void increaseStock(Integer quantity) {
        if (this.stock == null) {
            this.stock = 0;
        }
        this.stock += quantity;
        this.updatedAt = LocalDateTime.now();
    }
}
