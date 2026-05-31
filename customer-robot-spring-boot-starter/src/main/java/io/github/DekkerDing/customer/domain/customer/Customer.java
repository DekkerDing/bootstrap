package io.github.DekkerDing.customer.domain.customer;

import java.time.LocalDateTime;

/**
 * 客户领域模型
 * Customer Domain Model
 */
public class Customer {

    /**
     * 客户唯一标识
     * Unique customer identifier
     */
    private Long id;

    /**
     * 客户编号
     * Customer number
     */
    private String customerNo;

    /**
     * 客户名称
     * Customer name
     */
    private String name;

    /**
     * 联系电话
     * Contact phone
     */
    private String phone;

    /**
     * 电子邮箱
     * Email address
     */
    private String email;

    /**
     * 客户来源（WEB/APP/WECHAT/PHONE）
     * Customer source
     */
    private String source;

    /**
     * 客户类型（VIP/NORMAL）
     * Customer type
     */
    private String type;

    /**
     * 客户状态（ACTIVE/INACTIVE/BLOCKED）
     * Customer status
     */
    private String status;

    /**
     * 备注
     * Remarks
     */
    private String remarks;

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

    public Customer() {
        this.type = "NORMAL";
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        this.updatedAt = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * 检查客户是否活跃
     * Check if customer is active
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    /**
     * 检查是否为 VIP 客户
     * Check if customer is VIP
     */
    public boolean isVip() {
        return "VIP".equals(this.type);
    }
}
