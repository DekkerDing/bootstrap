package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 商品 JPA 仓储接口
 * Product JPA Repository Interface
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    /**
     * 根据分类 ID 查询商品列表
     * Find products by category ID
     */
    List<ProductEntity> findByCategoryId(Long categoryId);

    /**
     * 根据状态查询商品列表
     * Find products by status
     */
    List<ProductEntity> findByStatus(String status);

    /**
     * 根据分类 ID 和状态查询商品列表
     * Find products by category ID and status
     */
    List<ProductEntity> findByCategoryIdAndStatus(Long categoryId, String status);

    /**
     * 根据名称模糊查询商品
     * Find products by name containing
     */
    List<ProductEntity> findByNameContaining(String name);

    /**
     * 检查库存是否足够
     * Check if stock is sufficient
     */
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :productId AND p.stock >= :quantity")
    Optional<ProductEntity> checkStockAvailable(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
