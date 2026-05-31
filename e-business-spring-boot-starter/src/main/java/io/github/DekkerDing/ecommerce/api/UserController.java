package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.user.Address;
import io.github.DekkerDing.ecommerce.domain.user.User;
import io.github.DekkerDing.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 * User Controller
 */
@RestController
@RequestMapping("/api/users")
@ConditionalOnProperty(prefix = "e-commerce.modules", name = "user-enabled", havingValue = "true", matchIfMissing = true)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ========== 用户管理 / User Management ==========

    /**
     * 创建用户
     * Create user
     */
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 更新用户
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除用户
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 根据 ID 查询用户
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 根据用户名查询用户
     * Get user by username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<User>> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 根据状态查询用户列表
     * Get users by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByStatus(@PathVariable String status) {
        List<User> users = userService.getUsersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * 检查用户名是否存在
     * Check if username exists
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(exists));
    }

    /**
     * 检查邮箱是否存在
     * Check if email exists
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(exists));
    }

    // ========== 地址管理 / Address Management ==========

    /**
     * 创建地址
     * Create address
     */
    @PostMapping("/{userId}/addresses")
    public ResponseEntity<ApiResponse<Address>> createAddress(@PathVariable Long userId, @Valid @RequestBody Address address) {
        address.setUserId(userId);
        Address created = userService.createAddress(address);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 更新地址
     * Update address
     */
    @PutMapping("/{userId}/addresses/{id}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(@PathVariable Long userId, @PathVariable Long id, @Valid @RequestBody Address address) {
        address.setId(id);
        address.setUserId(userId);
        Address updated = userService.updateAddress(address);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除地址
     * Delete address
     */
    @DeleteMapping("/{userId}/addresses/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Long userId, @PathVariable Long id) {
        userService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 根据 ID 查询地址
     * Get address by ID
     */
    @GetMapping("/{userId}/addresses/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable Long userId, @PathVariable Long id) {
        Address address = userService.getAddressById(id);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    /**
     * 查询用户所有地址
     * Get addresses by user ID
     */
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<ApiResponse<List<Address>>> getAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = userService.getAddressesByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    /**
     * 查询默认地址
     * Get default address
     */
    @GetMapping("/{userId}/addresses/default")
    public ResponseEntity<ApiResponse<Address>> getDefaultAddress(@PathVariable Long userId) {
        Address address = userService.getDefaultAddress(userId);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    /**
     * 设置默认地址
     * Set default address
     */
    @PostMapping("/{userId}/addresses/{id}/default")
    public ResponseEntity<ApiResponse<Address>> setDefaultAddress(@PathVariable Long userId, @PathVariable Long id) {
        Address address = userService.setDefaultAddress(userId, id);
        return ResponseEntity.ok(ApiResponse.success(address));
    }
}
