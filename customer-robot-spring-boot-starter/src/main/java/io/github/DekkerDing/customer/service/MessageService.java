package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.message.Message;

import java.util.List;

/**
 * 消息服务接口
 * Message Service Interface
 */
public interface MessageService {

    Message sendMessage(Message message);

    Message getMessageById(Long id);

    List<Message> getConversationMessages(Long conversationId);

    long countUnreadMessages(Long conversationId);

    void markAsRead(Long messageId);

    List<Message> getUnreadMessages(Long conversationId);
}
