package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户 JPA 仓储接口
 * User JPA Repository Interface
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 根据用户名查询用户
     * Find user by username
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * 根据电子邮箱查询用户
     * Find user by email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * 根据电话号码查询用户
     * Find user by phone
     */
    Optional<UserEntity> findByPhone(String phone);

    /**
     * 根据状态查询用户列表
     * Find users by status
     */
    List<UserEntity> findByStatus(String status);

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
}
