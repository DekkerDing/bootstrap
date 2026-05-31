package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.dialogue.DialogueLog;
import java.util.List;

public interface DialogueLogRepository {
    DialogueLog save(DialogueLog dialogueLog);
    List<DialogueLog> findBySessionId(Long sessionId);
    List<DialogueLog> findByUserId(Long userId);
}
