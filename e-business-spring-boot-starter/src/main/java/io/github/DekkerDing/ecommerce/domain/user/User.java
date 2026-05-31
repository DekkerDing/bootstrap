package io.github.DekkerDing.ecommerce.domain.user;

import java.time.LocalDateTime;

/**
 * 用户领域模型
 * User domain model
 */
public class User {

    /**
     * 用户唯一标识
     * Unique user identifier
     */
    private Long id;

    /**
     * 用户名（用于登录）
     * Username (for login)
     */
    private String username;

    /**
     * 电子邮件
     * Email address
     */
    private String email;

    /**
     * 电话号码
     * Phone number
     */
    private String phone;

    /**
     * 真实姓名
     * Real name
     */
    private String realName;

    /**
     * 用户状态：ACTIVE-活动，INACTIVE-非活动，SUSPENDED-暂停
     * User status: ACTIVE-active, INACTIVE-inactive, SUSPENDED-suspended
     */
    private String status;

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

    public User() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
     * 检查用户是否活动
     * Check if user is active
     *
     * @return 是否活动 / whether active
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    /**
     * 检查用户是否可以登录
     * Check if user can login
     *
     * @return 是否可以登录 / whether can login
     */
    public boolean canLogin() {
        return "ACTIVE".equals(this.status);
    }
}
