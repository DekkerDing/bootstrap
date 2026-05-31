package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.ticket.Ticket;
import io.github.DekkerDing.customer.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工单 API 控制器
 * Ticket Controller
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * 创建工单
     * Create ticket
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody Ticket ticket) {
        Ticket created = ticketService.createTicket(ticket);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 获取工单详情
     * Get ticket details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicket(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticket -> ResponseEntity.ok(ApiResponse.success(ticket)))
                .orElse(ResponseEntity.ok(ApiResponse.notFound("工单不存在 / Ticket not found")));
    }

    /**
     * 按工单号查询
     * Get ticket by ticket no
     */
    @GetMapping("/no/{ticketNo}")
    public ResponseEntity<ApiResponse<Ticket>> getByTicketNo(@PathVariable String ticketNo) {
        return ticketService.findByTicketNo(ticketNo)
                .map(ticket -> ResponseEntity.ok(ApiResponse.success(ticket)))
                .orElse(ResponseEntity.ok(ApiResponse.notFound("工单不存在 / Ticket not found")));
    }

    /**
     * 获取客户工单列表
     * Get customer tickets
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Ticket>>> getCustomerTickets(@PathVariable Long customerId) {
        List<Ticket> tickets = ticketService.findByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    /**
     * 获取客服工单列表
     * Get agent tickets
     */
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<ApiResponse<List<Ticket>>> getAgentTickets(@PathVariable Long agentId) {
        List<Ticket> tickets = ticketService.findByAssignedTo(agentId);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    /**
     * 获取待处理工单
     * Get pending tickets
     */
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Ticket>>> getPendingTickets() {
        List<Ticket> tickets = ticketService.findPendingTickets();
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    /**
     * 按状态获取工单
     * Get tickets by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Ticket>>> getByStatus(@PathVariable String status) {
        List<Ticket> tickets = ticketService.findByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    /**
     * 分配工单
     * Assign ticket
     */
    @PostMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<Ticket>> assignTicket(
            @PathVariable Long id,
            @RequestParam Long agentId) {
        Ticket ticket = ticketService.assignTo(id, agentId);
        return ResponseEntity.ok(ApiResponse.success(ticket));
    }

    /**
     * 解决工单
     * Resolve ticket
     */
    @PostMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<Ticket>> resolveTicket(
            @PathVariable Long id,
            @RequestParam Long resolverId,
            @RequestParam String resolution) {
        Ticket ticket = ticketService.resolve(id, resolverId, resolution);
        return ResponseEntity.ok(ApiResponse.success(ticket));
    }

    /**
     * 关闭工单
     * Close ticket
     */
    @PostMapping("/{id}/close")
    public ResponseEntity<ApiResponse<Ticket>> closeTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.close(id);
        return ResponseEntity.ok(ApiResponse.success(ticket));
    }
}
