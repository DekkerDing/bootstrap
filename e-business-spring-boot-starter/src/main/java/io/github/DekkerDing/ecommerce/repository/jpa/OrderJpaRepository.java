package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单 JPA 仓储接口
 * Order JPA Repository Interface
 */
@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * 根据订单号查询订单
     * Find order by order number
     */
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    /**
     * 根据用户 ID 查询订单列表
     * Find orders by user ID
     */
    List<OrderEntity> findByUserId(Long userId);

    /**
     * 根据用户 ID 分页查询订单
     * Find orders by user ID with pagination
     */
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    /**
     * 根据用户 ID 和状态查询订单
     * Find orders by user ID and status
     */
    List<OrderEntity> findByUserIdAndStatus(Long userId, String status);

    /**
     * 根据状态查询订单列表
     * Find orders by status
     */
    List<OrderEntity> findByStatus(String status);

    /**
     * 查询待处理订单（状态为 PENDING）
     * Find pending orders
     */
    @Query("SELECT o FROM OrderEntity o WHERE o.status = 'PENDING' ORDER BY o.createdAt DESC")
    List<OrderEntity> findPendingOrders();
}
