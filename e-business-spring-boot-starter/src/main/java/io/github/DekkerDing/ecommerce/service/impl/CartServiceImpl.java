package io.github.DekkerDing.ecommerce.service.impl;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.domain.cart.CartItem;
import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.CartRepository;
import io.github.DekkerDing.ecommerce.repository.ProductRepository;
import io.github.DekkerDing.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 购物车服务实现
 * Shopping Cart Service Implementation
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            // 创建新购物车
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
    }

    @Override
    @Transactional
    public Cart addItem(Long userId, Long productId, Integer quantity) {
        // 校验商品存在
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在 / Product not found"));

        // 校验库存
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("库存不足 / Insufficient stock");
        }

        // 获取或创建购物车
        Cart cart = getCartByUserId(userId);

        // 检查是否已存在该商品
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // 更新数量
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("库存不足 / Insufficient stock");
            }
            item.setQuantity(newQuantity);
            item.setPrice(product.getPrice());
        } else {
            // 添加新项
            CartItem newItem = new CartItem(productId, null, product.getName(), product.getPrice(), quantity);
            newItem.setCartId(cart.getId());
            newItem.setProductImage(product.getImage());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long userId, Long itemId, Integer quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("购物车项不存在 / Cart item not found"));

        if (quantity < 1) {
            throw new IllegalArgumentException("数量必须大于 0 / Quantity must be greater than 0");
        }

        // 校验库存
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("商品不存在 / Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("库存不足 / Insufficient stock");
        }

        item.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart deleteItem(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("购物车项不存在 / Cart item not found"));

        cart.removeItem(itemId);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart toggleItemSelection(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("购物车项不存在 / Cart item not found"));

        item.toggleSelected();
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.clear();
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal calculateSelectedTotal(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getSelectedTotalAmount();
    }

    @Override
    @Transactional
    public Cart refreshCart(Long userId) {
        Cart cart = getCartByUserId(userId);

        // 更新每个商品项的状态
        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);

            if (product == null) {
                item.setOnSale(false);
                item.setHasStock(false);
            } else {
                item.setOnSale(product.getStatus().equals("ACTIVE"));
                item.setHasStock(product.getStock() > 0);
                item.setStock(product.getStock());

                // 更新价格（如果商品价格有变化）
                if (!item.getPrice().equals(product.getPrice())) {
                    item.setPrice(product.getPrice());
                }
            }
        }

        return cartRepository.save(cart);
    }
}
