package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

/**
 * 客户仓储接口
 * Customer Repository Interface
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    Customer update(Customer customer);

    void deleteById(Long id);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByCustomerNo(String customerNo);

    List<Customer> findByPhone(String phone);

    List<Customer> findByEmail(String email);

    List<Customer> findByStatus(String status);

    List<Customer> findBySource(String source);

    List<Customer> findAll();

    boolean existsByCustomerNo(String customerNo);

    boolean existsByPhone(String phone);
}
