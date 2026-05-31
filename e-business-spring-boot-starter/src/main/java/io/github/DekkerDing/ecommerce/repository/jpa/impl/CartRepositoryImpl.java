package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.repository.CartRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.CartJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.CartEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private final CartJpaRepository cartJpaRepository;
    private final CartMapper cartMapper;
    @Autowired
    public CartRepositoryImpl(CartJpaRepository cartJpaRepository, CartMapper cartMapper) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartMapper = cartMapper;
    }
    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartJpaRepository.findByUserIdWithItems(userId).map(cartMapper::toDomain);
    }
    @Override
    public Cart save(Cart cart) {
        CartEntity entity = cartMapper.toEntity(cart);
        CartEntity savedEntity = cartJpaRepository.save(entity);
        return cartMapper.toDomain(savedEntity);
    }
    @Override
    public void deleteById(Long id) {
        cartJpaRepository.deleteById(id);
    }
    @Override
    public void deleteByUserId(Long userId) {
        cartJpaRepository.deleteByUserId(userId);
    }
    @Override
    public boolean existsByUserId(Long userId) {
        return cartJpaRepository.existsByUserId(userId);
    }
}
