package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.customer.Customer;
import io.github.DekkerDing.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@Valid @RequestBody Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        customer.setId(id);
        Customer updated = customerService.updateCustomer(customer);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponse.success(customers));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Customer>>> getCustomersByStatus(@PathVariable String status) {
        List<Customer> customers = customerService.findByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(customers));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponse<List<Customer>>> getCustomersByPhone(@PathVariable String phone) {
        List<Customer> customers = customerService.findByPhone(phone);
        return ResponseEntity.ok(ApiResponse.success(customers));
    }
}
