package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.dialogue.DialogueIntent;
import io.github.DekkerDing.customer.domain.dialogue.DialogueIntent.IntentType;
import io.github.DekkerDing.customer.domain.dialogue.DialogueLog;
import io.github.DekkerDing.customer.repository.DialogueIntentRepository;
import io.github.DekkerDing.customer.repository.DialogueLogRepository;
import io.github.DekkerDing.customer.repository.KnowledgeBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DialogueEngineService {
    private final DialogueIntentRepository dialogueIntentRepository;
    private final DialogueLogRepository dialogueLogRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    @Autowired
    public DialogueEngineService(DialogueIntentRepository dialogueIntentRepository,
                                DialogueLogRepository dialogueLogRepository,
                                KnowledgeBaseRepository knowledgeBaseRepository) {
        this.dialogueIntentRepository = dialogueIntentRepository;
        this.dialogueLogRepository = dialogueLogRepository;
        this.knowledgeBaseRepository = knowledgeBaseRepository;
    }

    public DialogueLog processUserInput(Long userId, Long sessionId, String userInput) {
        DialogueLog log = new DialogueLog();
        log.setUserId(userId);
        log.setConversationId(sessionId);
        log.setUserInput(userInput);

        DialogueIntent matchedIntent = findMatchingIntent(userInput);
        if (matchedIntent != null) {
            log.setIntent(matchedIntent.getIntentCode());
            log.setConfidence(0.9);
            log.setBotResponse(generateResponse(matchedIntent, userInput));

            if (matchedIntent.getIntentType() == IntentType.ESCALATION) {
                log.setBotResponse("您的问题已转接给人工客服，请稍候...");
            }
        } else {
            String knowledgeAnswer = searchKnowledgeBase(userInput);
            if (knowledgeAnswer != null) {
                log.setIntent("KNOWLEDGE_SEARCH");
                log.setConfidence(0.7);
                log.setBotResponse(knowledgeAnswer);
            } else {
                log.setIntent("UNKNOWN");
                log.setConfidence(0.0);
                log.setBotResponse("抱歉，我没有理解您的问题。请换一种说法或联系人工客服。");
            }
        }

        return dialogueLogRepository.save(log);
    }

    private DialogueIntent findMatchingIntent(String input) {
        List<DialogueIntent> activeIntents = dialogueIntentRepository.findByActiveStatus();
        return activeIntents.stream()
                .filter(intent -> intent.matchKeyword(input))
                .max(Comparator.comparing(DialogueIntent::getPriority))
                .orElse(null);
    }

    private String generateResponse(DialogueIntent intent, String userInput) {
        String template = intent.getResponseTemplate();
        if (template != null && !template.isEmpty()) {
            return template;
        }
        return "感谢您的咨询，我们会尽快处理。";
    }

    private String searchKnowledgeBase(String question) {
        List<io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase> results =
                knowledgeBaseRepository.searchByKeyword(question);
        if (!results.isEmpty()) {
            return results.get(0).getAnswer();
        }
        return null;
    }

    public List<DialogueLog> getSessionLogs(Long sessionId) {
        return dialogueLogRepository.findBySessionId(sessionId);
    }

    public List<DialogueLog> getUserLogs(Long userId) {
        return dialogueLogRepository.findByUserId(userId);
    }
}
