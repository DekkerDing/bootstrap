package io.github.DekkerDing.customer.domain.session;

import java.time.LocalDateTime;

/**
 * 会话领域模型
 * Session Domain Model
 */
public class Session {

    /**
     * 会话唯一标识
     * Unique session identifier
     */
    private Long id;

    /**
     * 会话令牌
     * Session token
     */
    private String token;

    /**
     * 客户 ID
     * Customer ID
     */
    private Long customerId;

    /**
     * 客户 IP 地址
     * Customer IP address
     */
    private String ipAddress;

    /**
     * 用户代理
     * User agent
     */
    private String userAgent;

    /**
     * 登录时间
     * Login time
     */
    private LocalDateTime loginAt;

    /**
     * 最后活跃时间
     * Last active time
     */
    private LocalDateTime lastActiveAt;

    /**
     * 过期时间
     * Expiry time
     */
    private LocalDateTime expiryAt;

    /**
     * 会话状态（ACTIVE/EXPIRED/LOGOUT）
     * Session status
     */
    private String status;

    /**
     * 创建时间
     * Creation timestamp
     */
    private LocalDateTime createdAt;

    public Session() {
        this.status = "ACTIVE";
        this.loginAt = LocalDateTime.now();
        this.lastActiveAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        // 默认 24 小时过期
        this.expiryAt = LocalDateTime.now().plusHours(24);
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public LocalDateTime getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(LocalDateTime expiryAt) {
        this.expiryAt = expiryAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 检查会话是否活跃
     * Check if session is active
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status) && !isExpired();
    }

    /**
     * 检查会话是否已过期
     * Check if session is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryAt);
    }

    /**
     * 更新最后活跃时间
     * Update last active time
     */
    public void updateLastActiveTime() {
        this.lastActiveAt = LocalDateTime.now();
    }

    /**
     * 使会话过期
     * Expire session
     */
    public void expire() {
        this.status = "EXPIRED";
    }

    /**
     * 登出
     * Logout
     */
    public void logout() {
        this.status = "LOGOUT";
    }
}
