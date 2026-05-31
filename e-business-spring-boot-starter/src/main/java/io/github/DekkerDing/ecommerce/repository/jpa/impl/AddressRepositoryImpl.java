package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import io.github.DekkerDing.ecommerce.repository.AddressRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.AddressJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.AddressEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 地址 JPA 仓储实现
 * Address JPA Repository Implementation
 */
@Repository
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository jpaRepository;
    private final AddressMapper mapper;

    @Autowired
    public AddressRepositoryImpl(AddressJpaRepository jpaRepository, AddressMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Address save(Address address) {
        AddressEntity entity = mapper.toEntity(address);
        AddressEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Address update(Address address) {
        AddressEntity entity = mapper.toEntity(address);
        entity.setId(address.getId());
        AddressEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Address> findByUserId(Long userId) {
        return mapper.toDomainList(jpaRepository.findByUserId(userId));
    }

    @Override
    public Optional<Address> findDefaultByUserId(Long userId) {
        return jpaRepository.findByUserIdAndIsDefaultTrue(userId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        clearDefaultAddresses(userId);
        AddressEntity entity = jpaRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + addressId));
        if (!entity.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Address does not belong to user");
        }
        entity.setIsDefault(true);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void clearDefaultAddresses(Long userId) {
        List<AddressEntity> addresses = jpaRepository.findByUserId(userId);
        for (AddressEntity address : addresses) {
            if (address.getIsDefault()) {
                address.setIsDefault(false);
                jpaRepository.save(address);
            }
        }
    }
}
