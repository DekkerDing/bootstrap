package io.github.DekkerDing.ecommerce.domain.user;

/**
 * 地址领域模型
 * Address domain model
 */
public class Address {

    /**
     * 地址唯一标识
     * Unique address identifier
     */
    private Long id;

    /**
     * 用户 ID
     * User ID
     */
    private Long userId;

    /**
     * 收件人姓名
     * Receiver name
     */
    private String receiverName;

    /**
     * 联系电话
     * Contact phone
     */
    private String phone;

    /**
     * 省份
     * Province
     */
    private String province;

    /**
     * 城市
     * City
     */
    private String city;

    /**
     * 区/县
     * District
     */
    private String district;

    /**
     * 详细地址
     * Detailed address
     */
    private String detailAddress;

    /**
     * 邮政编码
     * Postal code
     */
    private String postalCode;

    /**
     * 是否为默认地址
     * Whether this is the default address
     */
    private Boolean isDefault;

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

    public Address() {
        this.isDefault = false;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
     * 获取完整地址字符串
     * Get full address string
     *
     * @return 完整地址 / full address
     */
    public String getFullAddress() {
        return this.province + this.city + this.district + this.detailAddress;
    }
}
