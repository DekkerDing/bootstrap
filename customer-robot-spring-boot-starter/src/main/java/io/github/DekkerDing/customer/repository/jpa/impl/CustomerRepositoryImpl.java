package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.customer.Customer;
import io.github.DekkerDing.customer.repository.CustomerRepository;
import io.github.DekkerDing.customer.repository.jpa.CustomerJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.CustomerEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;
    private final CustomerMapper mapper;

    @Autowired
    public CustomerRepositoryImpl(CustomerJpaRepository jpaRepository, CustomerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        CustomerEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        entity.setId(customer.getId());
        CustomerEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Customer> findByCustomerNo(String customerNo) {
        return jpaRepository.findByCustomerNo(customerNo).map(mapper::toDomain);
    }

    @Override
    public List<Customer> findByPhone(String phone) {
        return mapper.toDomainList(jpaRepository.findByPhone(phone));
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return mapper.toDomainList(jpaRepository.findByEmail(email));
    }

    @Override
    public List<Customer> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public List<Customer> findBySource(String source) {
        return mapper.toDomainList(jpaRepository.findBySource(source));
    }

    @Override
    public List<Customer> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public boolean existsByCustomerNo(String customerNo) {
        return jpaRepository.existsByCustomerNo(customerNo);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return jpaRepository.existsByPhone(phone);
    }
}
