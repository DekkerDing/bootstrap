package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.user.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储接口
 * User Repository Interface
 * <p>
 * 框架无关的用户数据访问抽象
 * Framework-agnostic user data access abstraction
 * </p>
 */
public interface UserRepository {

    /**
     * 保存用户
     * Save user
     *
     * @param user 用户 / user
     * @return 保存后的用户 / saved user
     */
    User save(User user);

    /**
     * 更新用户
     * Update user
     *
     * @param user 用户 / user
     * @return 更新后的用户 / updated user
     */
    User update(User user);

    /**
     * 根据 ID 删除用户
     * Delete user by ID
     *
     * @param id 用户 ID / user ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询用户
     * Find user by ID
     *
     * @param id 用户 ID / user ID
     * @return 用户 / user
     */
    Optional<User> findById(Long id);

    /**
     * 根据用户名查询用户
     * Find user by username
     *
     * @param username 用户名 / username
     * @return 用户 / user
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据电子邮箱查询用户
     * Find user by email
     *
     * @param email 电子邮箱 / email
     * @return 用户 / user
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据电话号码查询用户
     * Find user by phone
     *
     * @param phone 电话号码 / phone
     * @return 用户 / user
     */
    Optional<User> findByPhone(String phone);

    /**
     * 根据状态查询用户列表
     * Find users by status
     *
     * @param status 状态 / status
     * @return 用户列表 / user list
     */
    List<User> findByStatus(String status);

    /**
     * 检查用户名是否已存在
     * Check if username exists
     *
     * @param username 用户名 / username
     * @return 是否存在 / whether exists
     */
    boolean existsByUsername(String username);

    /**
     * 检查电子邮箱是否已存在
     * Check if email exists
     *
     * @param email 电子邮箱 / email
     * @return 是否存在 / whether exists
     */
    boolean existsByEmail(String email);
}
