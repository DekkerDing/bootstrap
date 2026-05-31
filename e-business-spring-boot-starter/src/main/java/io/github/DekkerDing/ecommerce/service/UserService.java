package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import io.github.DekkerDing.ecommerce.domain.user.User;

import java.util.List;

/**
 * 用户服务接口
 * User Service Interface
 */
public interface UserService {

    /**
     * 创建用户
     * Create user
     */
    User createUser(User user);

    /**
     * 更新用户
     * Update user
     */
    User updateUser(User user);

    /**
     * 根据 ID 删除用户
     * Delete user by ID
     */
    void deleteUser(Long id);

    /**
     * 根据 ID 查询用户
     * Find user by ID
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询用户
     * Find user by username
     */
    User getUserByUsername(String username);

    /**
     * 根据电子邮箱查询用户
     * Find user by email
     */
    User getUserByEmail(String email);

    /**
     * 根据电话号码查询用户
     * Find user by phone
     */
    User getUserByPhone(String phone);

    /**
     * 根据状态查询用户列表
     * Find users by status
     */
    List<User> getUsersByStatus(String status);

    /**
     * 检查用户名是否已存在
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * 检查电子邮箱是否已存在
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * 创建地址
     * Create address
     */
    Address createAddress(Address address);

    /**
     * 更新地址
     * Update address
     */
    Address updateAddress(Address address);

    /**
     * 删除地址
     * Delete address
     */
    void deleteAddress(Long id);

    /**
     * 根据 ID 查询地址
     * Find address by ID
     */
    Address getAddressById(Long id);

    /**
     * 根据用户 ID 查询地址列表
     * Find addresses by user ID
     */
    List<Address> getAddressesByUserId(Long userId);

    /**
     * 查询默认地址
     * Find default address
     */
    Address getDefaultAddress(Long userId);

    /**
     * 设置默认地址
     * Set default address
     */
    Address setDefaultAddress(Long userId, Long addressId);

    /**
     * 清除默认地址
     * Clear default addresses
     */
    void clearDefaultAddresses(Long userId);
}
