package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车项 JPA 仓储接口
 * Cart Item JPA Repository Interface
 */
@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {

    /**
     * 根据购物车 ID 查找所有购物车项
     * Find all items by cart ID
     */
    List<CartItemEntity> findByCartId(Long cartId);

    /**
     * 根据购物车 ID 和商品 ID 查找购物车项
     * Find item by cart ID and product ID
     */
    CartItemEntity findByCartIdAndProductId(Long cartId, Long productId);

    /**
     * 根据购物车 ID、商品 ID 和 SKU ID 查找购物车项
     * Find item by cart ID, product ID, and SKU ID
     */
    @Query("SELECT ci FROM CartItemEntity ci WHERE ci.cartId = :cartId AND ci.productId = :productId AND ci.skuId = :skuId")
    CartItemEntity findByCartIdAndProductIdAndSkuId(
            @Param("cartId") Long cartId,
            @Param("productId") Long productId,
            @Param("skuId") Long skuId
    );

    /**
     * 根据购物车 ID 和商品 ID 删除购物车项
     * Delete item by cart ID and product ID
     */
    void deleteByCartIdAndProductId(Long cartId, Long productId);

    /**
     * 根据购物车 ID 删除所有购物车项
     * Delete all items by cart ID
     */
    void deleteByCartId(Long cartId);

    /**
     * 统计购物车中的商品数量
     * Count items in cart
     */
    @Query("SELECT COUNT(ci) FROM CartItemEntity ci WHERE ci.cartId = :cartId")
    Long countByCartId(@Param("cartId") Long cartId);

    /**
     * 计算购物车总金额
     * Calculate total cart amount
     */
    @Query("SELECT SUM(ci.price * ci.quantity) FROM CartItemEntity ci WHERE ci.cartId = :cartId AND ci.selected = true")
    Double calculateTotalAmount(@Param("cartId") Long cartId);
}
