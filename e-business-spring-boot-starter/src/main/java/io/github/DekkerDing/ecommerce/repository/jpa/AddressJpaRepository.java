package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 地址 JPA 仓储接口
 * Address JPA Repository Interface
 */
@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {

    /**
     * 根据用户 ID 查询地址列表
     * Find addresses by user ID
     */
    List<AddressEntity> findByUserId(Long userId);

    /**
     * 根据用户 ID 查询默认地址
     * Find default address by user ID
     */
    Optional<AddressEntity> findByUserIdAndIsDefaultTrue(Long userId);

    /**
     * 检查用户是否有默认地址
     * Check if user has default address
     */
    boolean existsByUserIdAndIsDefaultTrue(Long userId);
}
