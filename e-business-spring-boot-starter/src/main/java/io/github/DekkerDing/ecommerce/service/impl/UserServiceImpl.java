package io.github.DekkerDing.ecommerce.service.impl;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import io.github.DekkerDing.ecommerce.domain.user.User;
import io.github.DekkerDing.ecommerce.repository.AddressRepository;
import io.github.DekkerDing.ecommerce.repository.UserRepository;
import io.github.DekkerDing.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现
 * User Service Implementation
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        if (user.getEmail() != null && existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userRepository.update(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + phone));
    }

    @Override
    public List<User> getUsersByStatus(String status) {
        return userRepository.findByStatus(status);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Address createAddress(Address address) {
        // 如果设置为默认地址，先清除其他默认地址
        // If setting as default address, clear other default addresses first
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            addressRepository.clearDefaultAddresses(address.getUserId());
        }
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Address address) {
        if (address.getId() == null) {
            throw new IllegalArgumentException("Address ID cannot be null");
        }
        return addressRepository.update(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));
    }

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address getDefaultAddress(Long userId) {
        return addressRepository.findDefaultByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Default address not found for user: " + userId));
    }

    @Override
    @Transactional
    public Address setDefaultAddress(Long userId, Long addressId) {
        addressRepository.setDefaultAddress(userId, addressId);
        return getAddressById(addressId);
    }

    @Override
    @Transactional
    public void clearDefaultAddresses(Long userId) {
        addressRepository.clearDefaultAddresses(userId);
    }
}
