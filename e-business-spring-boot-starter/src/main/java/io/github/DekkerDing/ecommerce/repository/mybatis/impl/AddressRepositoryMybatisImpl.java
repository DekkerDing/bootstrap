package io.github.DekkerDing.ecommerce.repository.mybatis.impl;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import io.github.DekkerDing.ecommerce.repository.AddressRepository;
import io.github.DekkerDing.ecommerce.repository.mybatis.AddressMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 地址 MyBatis 仓储实现
 * Address MyBatis Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class AddressRepositoryMybatisImpl implements AddressRepository {

    private final AddressMybatisMapper mapper;

    @Autowired
    public AddressRepositoryMybatisImpl(AddressMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Address save(Address address) {
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        if (address.getIsDefault() == null) {
            address.setIsDefault(false);
        }
        mapper.insert(address);
        return address;
    }

    @Override
    @Transactional
    public Address update(Address address) {
        address.setUpdatedAt(LocalDateTime.now());
        mapper.update(address);
        return address;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Address> findById(Long id) {
        Address address = mapper.findById(id);
        return Optional.ofNullable(address);
    }

    @Override
    public List<Address> findByUserId(Long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public Optional<Address> findDefaultByUserId(Long userId) {
        Address address = mapper.findDefaultByUserId(userId);
        return Optional.ofNullable(address);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        clearDefaultAddresses(userId);
        Address address = findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + addressId));
        if (!address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Address does not belong to user");
        }
        mapper.setDefaultAddress(addressId);
    }

    @Override
    @Transactional
    public void clearDefaultAddresses(Long userId) {
        if (mapper.hasDefaultAddress(userId)) {
            mapper.clearDefaultAddresses(userId);
        }
    }
}
