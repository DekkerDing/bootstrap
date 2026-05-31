package io.github.DekkerDing.customer.repository.mybatis.impl;

import io.github.DekkerDing.customer.domain.customer.Customer;
import io.github.DekkerDing.customer.repository.CustomerRepository;
import io.github.DekkerDing.customer.repository.mybatis.CustomerMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "mybatis")
public class CustomerRepositoryMybatisImpl implements CustomerRepository {

    private final CustomerMybatisMapper mapper;

    @Autowired
    public CustomerRepositoryMybatisImpl(CustomerMybatisMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        mapper.insert(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        customer.setUpdatedAt(LocalDateTime.now());
        mapper.update(customer);
        return customer;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Customer customer = mapper.findById(id);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByCustomerNo(String customerNo) {
        Customer customer = mapper.findByCustomerNo(customerNo);
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findByPhone(String phone) {
        return mapper.findByPhone(phone);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return mapper.findByEmail(email);
    }

    @Override
    public List<Customer> findByStatus(String status) {
        return mapper.findByStatus(status);
    }

    @Override
    public List<Customer> findBySource(String source) {
        return mapper.findBySource(source);
    }

    @Override
    public List<Customer> findAll() {
        return mapper.findAll();
    }

    @Override
    public boolean existsByCustomerNo(String customerNo) {
        return mapper.existsByCustomerNo(customerNo);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return mapper.existsByPhone(phone);
    }
}
