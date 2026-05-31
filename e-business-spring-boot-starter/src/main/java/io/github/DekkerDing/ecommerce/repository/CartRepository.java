package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.domain.cart.CartItem;

import java.util.Optional;

/**
 * 购物车仓储接口
 * Shopping Cart Repository Interface
 */
public interface CartRepository {

    /**
     * 根据用户 ID 查找购物车
     * Find cart by user ID
     */
    Optional<Cart> findByUserId(Long userId);

    /**
     * 保存购物车
     * Save cart
     */
    Cart save(Cart cart);

    /**
     * 删除购物车
     * Delete cart
     */
    void deleteById(Long id);

    /**
     * 根据用户 ID 删除购物车
     * Delete cart by user ID
     */
    void deleteByUserId(Long userId);

    /**
     * 检查用户是否有购物车
     * Check if user has cart
     */
    boolean existsByUserId(Long userId);
}
