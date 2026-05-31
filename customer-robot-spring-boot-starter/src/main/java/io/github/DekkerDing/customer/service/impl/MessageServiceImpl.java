package io.github.DekkerDing.customer.service.impl;

import io.github.DekkerDing.customer.domain.message.Message;
import io.github.DekkerDing.customer.repository.MessageRepository;
import io.github.DekkerDing.customer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("消息不存在 / Message not found"));
    }

    @Override
    public List<Message> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    @Override
    public long countUnreadMessages(Long conversationId) {
        return messageRepository.countUnreadMessages(conversationId);
    }

    @Override
    @Transactional
    public void markAsRead(Long messageId) {
        Message message = getMessageById(messageId);
        message.markAsRead();
        messageRepository.update(message);
    }

    @Override
    public List<Message> getUnreadMessages(Long conversationId) {
        return messageRepository.findByConversationIdAndIsRead(conversationId, false);
    }
}
