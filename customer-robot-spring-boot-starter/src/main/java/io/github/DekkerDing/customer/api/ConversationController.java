package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.conversation.Conversation;
import io.github.DekkerDing.customer.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Conversation>> createConversation(@Valid @RequestBody Conversation conversation) {
        Conversation created = conversationService.createConversation(conversation);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Conversation>> getConversationById(@PathVariable Long id) {
        Conversation conversation = conversationService.getConversationById(id);
        return ResponseEntity.ok(ApiResponse.success(conversation));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Conversation>>> getCustomerConversations(@PathVariable Long customerId) {
        List<Conversation> conversations = conversationService.getCustomerConversations(customerId);
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<ApiResponse<List<Conversation>>> getAgentConversations(@PathVariable Long agentId) {
        List<Conversation> conversations = conversationService.getAgentConversations(agentId);
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }

    @GetMapping("/waiting")
    public ResponseEntity<ApiResponse<List<Conversation>>> getWaitingConversations() {
        List<Conversation> conversations = conversationService.getWaitingConversations();
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<Conversation>> assignAgent(@PathVariable Long id, @RequestParam Long agentId) {
        Conversation conversation = conversationService.assignAgent(id, agentId);
        return ResponseEntity.ok(ApiResponse.success(conversation));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<ApiResponse<Conversation>> closeConversation(@PathVariable Long id) {
        Conversation conversation = conversationService.closeConversation(id);
        return ResponseEntity.ok(ApiResponse.success(conversation));
    }
}
