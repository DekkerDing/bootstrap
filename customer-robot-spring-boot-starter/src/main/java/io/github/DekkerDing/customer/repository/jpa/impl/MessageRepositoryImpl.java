package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.message.Message;
import io.github.DekkerDing.customer.repository.MessageRepository;
import io.github.DekkerDing.customer.repository.jpa.MessageJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.MessageEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageJpaRepository jpaRepository;
    private final MessageMapper mapper;

    @Autowired
    public MessageRepositoryImpl(MessageJpaRepository jpaRepository, MessageMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Message save(Message message) {
        MessageEntity entity = mapper.toEntity(message);
        MessageEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Message update(Message message) {
        MessageEntity entity = mapper.toEntity(message);
        entity.setId(message.getId());
        MessageEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Message> findByConversationId(Long conversationId) {
        return mapper.toDomainList(jpaRepository.findByConversationIdOrderByCreatedAtAsc(conversationId));
    }

    @Override
    public List<Message> findBySenderId(Long senderId) {
        return mapper.toDomainList(jpaRepository.findBySenderId(senderId));
    }

    @Override
    public List<Message> findByConversationIdAndIsRead(Long conversationId, Boolean isRead) {
        return mapper.toDomainList(jpaRepository.findByConversationIdAndIsRead(conversationId, isRead));
    }

    @Override
    public long countUnreadMessages(Long conversationId) {
        return jpaRepository.countUnreadMessages(conversationId);
    }

    @Override
    public List<Message> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }
}
