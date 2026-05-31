package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.message.Message;
import io.github.DekkerDing.customer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Message>> sendMessage(@Valid @RequestBody Message message) {
        Message sent = messageService.sendMessage(message);
        return ResponseEntity.ok(ApiResponse.success(sent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<ApiResponse<List<Message>>> getConversationMessages(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getConversationMessages(conversationId);
        return ResponseEntity.ok(ApiResponse.success(messages));
    }

    @GetMapping("/conversation/{conversationId}/unread")
    public ResponseEntity<ApiResponse<List<Message>>> getUnreadMessages(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getUnreadMessages(conversationId);
        return ResponseEntity.ok(ApiResponse.success(messages));
    }

    @GetMapping("/conversation/{conversationId}/count")
    public ResponseEntity<ApiResponse<Long>> countUnreadMessages(@PathVariable Long conversationId) {
        long count = messageService.countUnreadMessages(conversationId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
