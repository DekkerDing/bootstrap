package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.user.User;
import io.github.DekkerDing.ecommerce.repository.UserRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.UserMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户 MyBatis 仓储实现
 * User MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class UserRepositoryMybatisImpl implements UserRepository {

    private final UserMybatisMapper mapper;

    @Autowired
    public UserRepositoryMybatisImpl(UserMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        mapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        mapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = mapper.findById(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = mapper.findByUsername(username);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = mapper.findByEmail(email);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        User user = mapper.findByPhone(phone);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findByStatus(String status) {
        return mapper.findByStatus(status);
    }

    @Override
    public boolean existsByUsername(String username) {
        return mapper.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return mapper.existsByEmail(email);
    }
}
