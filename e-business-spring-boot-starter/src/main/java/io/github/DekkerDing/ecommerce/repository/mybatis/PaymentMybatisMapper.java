package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 支付 MyBatis Mapper 接口
 * Payment MyBatis Mapper Interface
 */
@Mapper
public interface PaymentMybatisMapper {

    /**
     * 插入支付信息
     * Insert payment
     */
    @Insert("INSERT INTO ecommerce_payment (order_id, transaction_id, channel, amount, status, callback_data, created_at, updated_at) " +
            "VALUES (#{orderId}, #{transactionId}, #{channel}, #{amount}, #{status}, #{callbackData}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Payment payment);

    /**
     * 更新支付信息
     * Update payment
     */
    @Update("UPDATE ecommerce_payment SET order_id = #{orderId}, transaction_id = #{transactionId}, " +
            "channel = #{channel}, amount = #{amount}, status = #{status}, callback_data = #{callbackData}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(Payment payment);

    /**
     * 根据 ID 删除支付信息
     * Delete payment by ID
     */
    @Delete("DELETE FROM ecommerce_payment WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询支付信息
     * Find payment by ID
     */
    @Select("SELECT * FROM ecommerce_payment WHERE id = #{id}")
    @ResultMap("paymentResultMap")
    Payment findById(Long id);

    /**
     * 根据订单 ID 查询支付信息
     * Find payment by order ID
     */
    @Select("SELECT * FROM ecommerce_payment WHERE order_id = #{orderId}")
    @ResultMap("paymentResultMap")
    Payment findByOrderId(Long orderId);

    /**
     * 根据交易 ID 查询支付信息
     * Find payment by transaction ID
     */
    @Select("SELECT * FROM ecommerce_payment WHERE transaction_id = #{transactionId}")
    @ResultMap("paymentResultMap")
    Payment findByTransactionId(String transactionId);

    /**
     * 根据状态查询支付列表
     * Find payments by status
     */
    @Select("SELECT * FROM ecommerce_payment WHERE status = #{status} ORDER BY created_at DESC")
    @ResultMap("paymentResultMap")
    List<Payment> findByStatus(String status);

    /**
     * 更新支付状态
     * Update payment status
     */
    @Update("UPDATE ecommerce_payment SET status = #{status}, updated_at = NOW() WHERE id = #{paymentId}")
    int updateStatus(@Param("paymentId") Long paymentId, @Param("status") String status);

    /**
     * 保存回调数据
     * Save callback data
     */
    @Update("UPDATE ecommerce_payment SET callback_data = #{callbackData}, updated_at = NOW() WHERE id = #{paymentId}")
    int saveCallbackData(@Param("paymentId") Long paymentId, @Param("callbackData") String callbackData);
}
