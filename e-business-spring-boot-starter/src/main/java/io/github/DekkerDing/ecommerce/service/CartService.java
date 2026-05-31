package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.domain.cart.CartItem;
import io.github.DekkerDing.ecommerce.domain.product.Product;

import java.math.BigDecimal;

/**
 * 购物车服务接口
 * Shopping Cart Service Interface
 */
public interface CartService {

    /**
     * 根据用户 ID 获取购物车
     * Get cart by user ID
     */
    Cart getCartByUserId(Long userId);

    /**
     * 添加商品到购物车
     * Add product to cart
     */
    Cart addItem(Long userId, Long productId, Integer quantity);

    /**
     * 更新购物车项数量
     * Update cart item quantity
     */
    Cart updateItemQuantity(Long userId, Long itemId, Integer quantity);

    /**
     * 删除购物车项
     * Delete cart item
     */
    Cart deleteItem(Long userId, Long itemId);

    /**
     * 切换购物车项选中状态
     * Toggle cart item selection
     */
    Cart toggleItemSelection(Long userId, Long itemId);

    /**
     * 清空购物车
     * Clear cart
     */
    void clearCart(Long userId);

    /**
     * 计算选中项总金额
     * Calculate selected items total amount
     */
    BigDecimal calculateSelectedTotal(Long userId);

    /**
     * 刷新购物车（更新商品状态和库存）
     * Refresh cart (update product status and stock)
     */
    Cart refreshCart(Long userId);
}
