package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.dialogue.DialogueLog;
import io.github.DekkerDing.customer.service.DialogueEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对话 API 控制器
 * Dialogue Controller
 */
@RestController
@RequestMapping("/api/dialogue")
public class DialogueController {

    private final DialogueEngineService dialogueEngineService;

    @Autowired
    public DialogueController(DialogueEngineService dialogueEngineService) {
        this.dialogueEngineService = dialogueEngineService;
    }

    /**
     * 处理用户输入
     * Process user input
     */
    @PostMapping("/process")
    public ResponseEntity<ApiResponse<DialogueLog>> processInput(
            @RequestParam Long userId,
            @RequestParam Long sessionId,
            @RequestParam String userInput) {
        DialogueLog log = dialogueEngineService.processUserInput(userId, sessionId, userInput);
        return ResponseEntity.ok(ApiResponse.success(log));
    }

    /**
     * 获取会话日志
     * Get session logs
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ApiResponse<List<DialogueLog>>> getSessionLogs(@PathVariable Long sessionId) {
        List<DialogueLog> logs = dialogueEngineService.getSessionLogs(sessionId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    /**
     * 获取用户日志
     * Get user logs
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<DialogueLog>>> getUserLogs(@PathVariable Long userId) {
        List<DialogueLog> logs = dialogueEngineService.getUserLogs(userId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }
}
