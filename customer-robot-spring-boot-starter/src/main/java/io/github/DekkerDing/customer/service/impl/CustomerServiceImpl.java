package io.github.DekkerDing.customer.service.impl;

import io.github.DekkerDing.customer.domain.customer.Customer;
import io.github.DekkerDing.customer.repository.CustomerRepository;
import io.github.DekkerDing.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (existsByCustomerNo(customer.getCustomerNo())) {
            throw new IllegalArgumentException("客户编号已存在 / Customer number already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("客户不存在 / Customer not found"));
    }

    @Override
    public Optional<Customer> findByCustomerNo(String customerNo) {
        return customerRepository.findByCustomerNo(customerNo);
    }

    @Override
    public List<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public List<Customer> findByStatus(String status) {
        return customerRepository.findByStatus(status);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean existsByCustomerNo(String customerNo) {
        return customerRepository.existsByCustomerNo(customerNo);
    }
}
