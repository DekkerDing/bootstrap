package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 购物车 JPA 仓储接口
 * Shopping Cart JPA Repository Interface
 */
@Repository
public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {

    /**
     * 根据用户 ID 查找购物车
     * Find cart by user ID
     */
    Optional<CartEntity> findByUserId(Long userId);

    /**
     * 检查用户是否有购物车
     * Check if cart exists by user ID
     */
    boolean existsByUserId(Long userId);

    /**
     * 根据用户 ID 删除购物车
     * Delete cart by user ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户 ID 查找购物车并关联加载购物车项
     * Find cart by user ID with items fetch joined
     */
    @Query("SELECT c FROM CartEntity c LEFT JOIN FETCH c.items WHERE c.userId = :userId")
    Optional<CartEntity> findByUserIdWithItems(@Param("userId") Long userId);
}
