package io.github.DekkerDing.ecommerce.domain.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private Long id;
    private Long userId;
    private Integer totalQuantity = 0;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItem> items = new ArrayList<>();
    
    public Cart() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addItem(CartItem item) {
        items.add(item);
        updateTotals();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeItem(Long itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        updateTotals();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateItem(CartItem item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                break;
            }
        }
        updateTotals();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void clear() {
        items.clear();
        this.totalQuantity = 0;
        this.totalAmount = BigDecimal.ZERO;
        this.updatedAt = LocalDateTime.now();
    }
    
    private void updateTotals() {
        this.totalQuantity = 0;
        this.totalAmount = BigDecimal.ZERO;
        for (CartItem item : items) {
            this.totalQuantity += item.getQuantity();
            if (Boolean.TRUE.equals(item.getSelected())) {
                if (item.getSubtotal() != null) {
                    this.totalAmount = this.totalAmount.add(item.getSubtotal());
                }
            }
        }
    }
    
    public List<CartItem> getSelectedItems() {
        return items.stream()
                .filter(item -> Boolean.TRUE.equals(item.getSelected()))
                .collect(Collectors.toList());
    }
    
    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }
    
    public BigDecimal getSelectedTotalAmount() {
        return items.stream()
                .filter(item -> Boolean.TRUE.equals(item.getSelected()))
                .map(item -> item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { 
        this.items = items != null ? items : new ArrayList<>();
        updateTotals();
    }
}
