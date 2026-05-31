package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单 MyBatis Mapper 接口
 * Order MyBatis Mapper Interface
 */
@Mapper
public interface OrderMybatisMapper {

    /**
     * 插入订单
     * Insert order
     */
    @Insert("INSERT INTO ecommerce_order (order_number, user_id, status, total_amount, created_at, updated_at) " +
            "VALUES (#{orderNumber}, #{userId}, #{status}, #{totalAmount}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    /**
     * 更新订单
     * Update order
     */
    @Update("UPDATE ecommerce_order SET order_number = #{orderNumber}, user_id = #{userId}, " +
            "status = #{status}, total_amount = #{totalAmount}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(Order order);

    /**
     * 根据 ID 删除订单
     * Delete order by ID
     */
    @Delete("DELETE FROM ecommerce_order WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询订单
     * Find order by ID
     */
    @Select("SELECT * FROM ecommerce_order WHERE id = #{id}")
    @ResultMap("orderResultMap")
    Order findById(Long id);

    /**
     * 根据订单号查询订单
     * Find order by order number
     */
    @Select("SELECT * FROM ecommerce_order WHERE order_number = #{orderNumber}")
    @ResultMap("orderResultMap")
    Order findByOrderNumber(String orderNumber);

    /**
     * 根据用户 ID 查询订单列表
     * Find orders by user ID
     */
    @Select("SELECT * FROM ecommerce_order WHERE user_id = #{userId} ORDER BY created_at DESC")
    @ResultMap("orderResultMap")
    List<Order> findByUserId(Long userId);

    /**
     * 根据用户 ID 和状态查询订单列表
     * Find orders by user ID and status
     */
    @Select("SELECT * FROM ecommerce_order WHERE user_id = #{userId} AND status = #{status} ORDER BY created_at DESC")
    @ResultMap("orderResultMap")
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 根据状态查询订单列表
     * Find orders by status
     */
    @Select("SELECT * FROM ecommerce_order WHERE status = #{status} ORDER BY created_at DESC")
    @ResultMap("orderResultMap")
    List<Order> findByStatus(String status);

    /**
     * 更新订单状态
     * Update order status
     */
    @Update("UPDATE ecommerce_order SET status = #{newStatus}, updated_at = NOW() WHERE id = #{orderId}")
    int updateStatus(@Param("orderId") Long orderId, @Param("newStatus") String newStatus);
}
