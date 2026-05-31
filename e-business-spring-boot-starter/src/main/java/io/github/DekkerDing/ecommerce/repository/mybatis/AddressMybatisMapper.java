package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 地址 MyBatis Mapper 接口
 * Address MyBatis Mapper Interface
 */
@Mapper
public interface AddressMybatisMapper {

    /**
     * 插入地址
     * Insert address
     */
    @Insert("INSERT INTO ecommerce_address (user_id, receiver_name, phone, province, city, district, detail_address, postal_code, is_default, created_at, updated_at) " +
            "VALUES (#{userId}, #{receiverName}, #{phone}, #{province}, #{city}, #{district}, #{detailAddress}, #{postalCode}, #{isDefault}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Address address);

    /**
     * 更新地址
     * Update address
     */
    @Update("UPDATE ecommerce_address SET user_id = #{userId}, receiver_name = #{receiverName}, phone = #{phone}, " +
            "province = #{province}, city = #{city}, district = #{district}, detail_address = #{detailAddress}, " +
            "postal_code = #{postalCode}, is_default = #{isDefault}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(Address address);

    /**
     * 根据 ID 删除地址
     * Delete address by ID
     */
    @Delete("DELETE FROM ecommerce_address WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询地址
     * Find address by ID
     */
    @Select("SELECT * FROM ecommerce_address WHERE id = #{id}")
    @ResultMap("addressResultMap")
    Address findById(Long id);

    /**
     * 根据用户 ID 查询地址列表
     * Find addresses by user ID
     */
    @Select("SELECT * FROM ecommerce_address WHERE user_id = #{userId} ORDER BY is_default DESC, created_at DESC")
    @ResultMap("addressResultMap")
    List<Address> findByUserId(Long userId);

    /**
     * 根据用户 ID 查询默认地址
     * Find default address by user ID
     */
    @Select("SELECT * FROM ecommerce_address WHERE user_id = #{userId} AND is_default = 1")
    @ResultMap("addressResultMap")
    Address findDefaultByUserId(Long userId);

    /**
     * 检查用户是否有默认地址
     * Check if user has default address
     */
    @Select("SELECT COUNT(*) > 0 FROM ecommerce_address WHERE user_id = #{userId} AND is_default = 1")
    boolean hasDefaultAddress(Long userId);

    /**
     * 清除用户的所有默认地址标记
     * Clear all default address marks for user
     */
    @Update("UPDATE ecommerce_address SET is_default = 0, updated_at = NOW() WHERE user_id = #{userId} AND is_default = 1")
    int clearDefaultAddresses(Long userId);

    /**
     * 设置默认地址
     * Set default address
     */
    @Update("UPDATE ecommerce_address SET is_default = 1, updated_at = NOW() WHERE id = #{addressId}")
    int setDefaultAddress(Long addressId);
}
