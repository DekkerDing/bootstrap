package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 购物车 API 控制器
 * Shopping Cart Controller
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 获取当前用户购物车
     * Get current user's shopping cart
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(@RequestHeader("X-User-Id") Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    /**
     * 添加商品到购物车
     * Add product to cart
     */
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Cart>> addItem(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity
    ) {
        Cart cart = cartService.addItem(userId, productId, quantity);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    /**
     * 更新购物车项数量
     * Update cart item quantity
     */
    @PutMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Cart>> updateItemQuantity(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {
        Cart cart = cartService.updateItemQuantity(userId, id, quantity);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    /**
     * 切换购物车项选中状态
     * Toggle cart item selection
     */
    @PutMapping("/items/{id}/select")
    public ResponseEntity<ApiResponse<Cart>> toggleItemSelection(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id
    ) {
        Cart cart = cartService.toggleItemSelection(userId, id);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    /**
     * 删除购物车项
     * Delete cart item
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Cart>> deleteItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id
    ) {
        Cart cart = cartService.deleteItem(userId, id);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    /**
     * 清空购物车
     * Clear shopping cart
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.<Void>success("购物车已清空 / Cart cleared", null));
    }

    /**
     * 获取选中项总金额
     * Get selected items total amount
     */
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotal(@RequestHeader("X-User-Id") Long userId) {
        BigDecimal total = cartService.calculateSelectedTotal(userId);
        return ResponseEntity.ok(ApiResponse.success(total));
    }

    /**
     * 刷新购物车（更新商品状态和库存）
     * Refresh cart (update product status and stock)
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Cart>> refreshCart(@RequestHeader("X-User-Id") Long userId) {
        Cart cart = cartService.refreshCart(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }
}
