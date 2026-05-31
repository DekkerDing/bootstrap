package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.message.Message;

import java.util.List;
import java.util.Optional;

/**
 * 消息仓储接口
 * Message Repository Interface
 */
public interface MessageRepository {

    Message save(Message message);

    Message update(Message message);

    void deleteById(Long id);

    Optional<Message> findById(Long id);

    List<Message> findByConversationId(Long conversationId);

    List<Message> findBySenderId(Long senderId);

    List<Message> findByConversationIdAndIsRead(Long conversationId, Boolean isRead);

    long countUnreadMessages(Long conversationId);

    List<Message> findAll();
}
