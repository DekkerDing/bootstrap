package io.github.DekkerDing.ecommerce.domain.product;

/**
 * 商品分类领域模型
 * Product category domain model
 */
public class Category {

    /**
     * 分类唯一标识
     * Unique category identifier
     */
    private Long id;

    /**
     * 分类名称
     * Category name
     */
    private String name;

    /**
     * 父分类 ID（顶级分类为 null）
     * Parent category ID (null for top-level categories)
     */
    private Long parentId;

    /**
     * 创建时间
     * Creation timestamp
     */
    private java.time.LocalDateTime createdAt;

    /**
     * 最后更新时间
     * Last update timestamp
     */
    private java.time.LocalDateTime updatedAt;

    public Category() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
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
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 检查是否为顶级分类
     * Check if this is a top-level category
     *
     * @return 是否为顶级分类 / whether top-level category
     */
    public boolean isTopLevel() {
        return this.parentId == null;
    }
}
