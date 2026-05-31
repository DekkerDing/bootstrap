package io.github.DekkerDing.ecommerce.repository.mybatis;

import io.github.DekkerDing.ecommerce.domain.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户 MyBatis Mapper 接口
 * User MyBatis Mapper Interface
 */
@Mapper
public interface UserMybatisMapper {

    /**
     * 插入用户
     * Insert user
     */
    @Insert("INSERT INTO ecommerce_user (username, email, phone, real_name, status, created_at, updated_at) " +
            "VALUES (#{username}, #{email}, #{phone}, #{realName}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 更新用户
     * Update user
     */
    @Update("UPDATE ecommerce_user SET username = #{username}, email = #{email}, phone = #{phone}, " +
            "real_name = #{realName}, status = #{status}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int update(User user);

    /**
     * 根据 ID 删除用户
     * Delete user by ID
     */
    @Delete("DELETE FROM ecommerce_user WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据 ID 查询用户
     * Find user by ID
     */
    @Select("SELECT * FROM ecommerce_user WHERE id = #{id}")
    @ResultMap("userResultMap")
    User findById(Long id);

    /**
     * 根据用户名查询用户
     * Find user by username
     */
    @Select("SELECT * FROM ecommerce_user WHERE username = #{username}")
    @ResultMap("userResultMap")
    User findByUsername(String username);

    /**
     * 根据电子邮箱查询用户
     * Find user by email
     */
    @Select("SELECT * FROM ecommerce_user WHERE email = #{email}")
    @ResultMap("userResultMap")
    User findByEmail(String email);

    /**
     * 根据电话号码查询用户
     * Find user by phone
     */
    @Select("SELECT * FROM ecommerce_user WHERE phone = #{phone}")
    @ResultMap("userResultMap")
    User findByPhone(String phone);

    /**
     * 根据状态查询用户列表
     * Find users by status
     */
    @Select("SELECT * FROM ecommerce_user WHERE status = #{status} ORDER BY created_at DESC")
    @ResultMap("userResultMap")
    List<User> findByStatus(String status);

    /**
     * 检查用户名是否已存在
     * Check if username exists
     */
    @Select("SELECT COUNT(*) > 0 FROM ecommerce_user WHERE username = #{username}")
    boolean existsByUsername(String username);

    /**
     * 检查电子邮箱是否已存在
     * Check if email exists
     */
    @Select("SELECT COUNT(*) > 0 FROM ecommerce_user WHERE email = #{email}")
    boolean existsByEmail(String email);
}
