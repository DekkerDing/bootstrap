package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.user.Address;

import java.util.List;
import java.util.Optional;

/**
 * 地址仓储接口
 * Address Repository Interface
 * <p>
 * 框架无关的地址数据访问抽象
 * Framework-agnostic address data access abstraction
 * </p>
 */
public interface AddressRepository {

    /**
     * 保存地址
     * Save address
     *
     * @param address 地址 / address
     * @return 保存后的地址 / saved address
     */
    Address save(Address address);

    /**
     * 更新地址
     * Update address
     *
     * @param address 地址 / address
     * @return 更新后的地址 / updated address
     */
    Address update(Address address);

    /**
     * 根据 ID 删除地址
     * Delete address by ID
     *
     * @param id 地址 ID / address ID
     */
    void deleteById(Long id);

    /**
     * 根据 ID 查询地址
     * Find address by ID
     *
     * @param id 地址 ID / address ID
     * @return 地址 / address
     */
    Optional<Address> findById(Long id);

    /**
     * 根据用户 ID 查询地址列表
     * Find addresses by user ID
     *
     * @param userId 用户 ID / user ID
     * @return 地址列表 / address list
     */
    List<Address> findByUserId(Long userId);

    /**
     * 根据用户 ID 查询默认地址
     * Find default address by user ID
     *
     * @param userId 用户 ID / user ID
     * @return 默认地址 / default address
     */
    Optional<Address> findDefaultByUserId(Long userId);

    /**
     * 设置默认地址
     * Set default address
     *
     * @param userId    用户 ID / user ID
     * @param addressId 地址 ID / address ID
     */
    void setDefaultAddress(Long userId, Long addressId);

    /**
     * 清除用户的所有默认地址标记
     * Clear all default address marks for user
     *
     * @param userId 用户 ID / user ID
     */
    void clearDefaultAddresses(Long userId);
}
