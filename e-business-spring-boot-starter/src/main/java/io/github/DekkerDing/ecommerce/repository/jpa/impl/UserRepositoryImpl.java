package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.user.User;
import io.github.DekkerDing.ecommerce.repository.UserRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.UserJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.UserEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户 JPA 仓储实现
 * User JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public User update(User user) {
        UserEntity entity = mapper.toEntity(user);
        entity.setId(user.getId());
        UserEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return jpaRepository.findByPhone(phone)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
