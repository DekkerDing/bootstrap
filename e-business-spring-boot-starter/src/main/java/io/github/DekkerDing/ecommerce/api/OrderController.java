package io.github.DekkerDing.ecommerce.api;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单控制器
 * Order Controller
 */
@RestController
@RequestMapping("/api/orders")
@ConditionalOnProperty(prefix = "e-commerce.modules", name = "order-enabled", havingValue = "true", matchIfMissing = true)
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 创建订单
     * Create order
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 更新订单
     * Update order
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        order.setId(id);
        Order updated = orderService.updateOrder(order);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除订单
     * Delete order
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 根据 ID 查询订单
     * Get order by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    /**
     * 根据订单号查询订单
     * Get order by order number
     */
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponse<Order>> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    /**
     * 根据用户 ID 查询订单列表
     * Get orders by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    /**
     * 根据用户 ID 和状态查询订单列表
     * Get orders by user ID and status
     */
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUserIdAndStatus(@PathVariable Long userId, @PathVariable String status) {
        List<Order> orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    /**
     * 根据状态查询订单列表
     * Get orders by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    /**
     * 更新订单状态
     * Update order status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(@PathVariable Long id, @RequestParam String newStatus) {
        Order order = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    /**
     * 取消订单
     * Cancel order
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }
}
