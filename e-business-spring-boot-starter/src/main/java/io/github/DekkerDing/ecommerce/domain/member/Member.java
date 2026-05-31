package io.github.DekkerDing.ecommerce.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Member {
    private Long id;
    private Long userId;
    private String level;
    private Integer growthValue;
    private Integer points;
    private LocalDate expDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum Level {
        NORMAL(0, "普通会员"),
        SILVER(1000, "银卡会员"),
        GOLD(5000, "金卡会员"),
        PLATINUM(20000, "铂金会员"),
        DIAMOND(50000, "钻石会员");
        
        private final int threshold;
        private final String displayName;
        
        Level(int threshold, String displayName) {
            this.threshold = threshold;
            this.displayName = displayName;
        }
        
        public int getThreshold() { return threshold; }
        public String getDisplayName() { return displayName; }
    }
    
    public Member() {
        this.level = "NORMAL";
        this.growthValue = 0;
        this.points = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addPoints(Integer points) {
        this.points += points;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void deductPoints(Integer points) {
        if (this.points < points) {
            throw new IllegalArgumentException("积分不足 / Insufficient points");
        }
        this.points -= points;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addGrowthValue(Integer value) {
        this.growthValue += value;
        this.updatedAt = LocalDateTime.now();
        checkLevelUpgrade();
    }
    
    private void checkLevelUpgrade() {
        for (Member.Level level : Member.Level.values()) {
            if (this.growthValue >= level.getThreshold() && level.name().compareTo(this.level) > 0) {
                this.level = level.name();
            }
        }
    }
    
    public boolean isExpired() {
        return expDate != null && LocalDate.now().isAfter(expDate);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Integer getGrowthValue() { return growthValue; }
    public void setGrowthValue(Integer growthValue) { this.growthValue = growthValue; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public LocalDate getExpDate() { return expDate; }
    public void setExpDate(LocalDate expDate) { this.expDate = expDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
