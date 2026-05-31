package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

/**
 * 客户服务接口
 * Customer Service Interface
 */
public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomerById(Long id);

    Optional<Customer> findByCustomerNo(String customerNo);

    List<Customer> findByPhone(String phone);

    List<Customer> findByStatus(String status);

    List<Customer> getAllCustomers();

    boolean existsByCustomerNo(String customerNo);
}
