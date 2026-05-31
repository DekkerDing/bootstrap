package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.conversation.Conversation;
import io.github.DekkerDing.customer.repository.ConversationRepository;
import io.github.DekkerDing.customer.repository.jpa.ConversationJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.ConversationEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class ConversationRepositoryImpl implements ConversationRepository {

    private final ConversationJpaRepository jpaRepository;
    private final ConversationMapper mapper;

    @Autowired
    public ConversationRepositoryImpl(ConversationJpaRepository jpaRepository, ConversationMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Conversation save(Conversation conversation) {
        ConversationEntity entity = mapper.toEntity(conversation);
        ConversationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Conversation update(Conversation conversation) {
        ConversationEntity entity = mapper.toEntity(conversation);
        entity.setId(conversation.getId());
        ConversationEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Conversation> findByConversationNo(String conversationNo) {
        return jpaRepository.findByConversationNo(conversationNo).map(mapper::toDomain);
    }

    @Override
    public List<Conversation> findByCustomerId(Long customerId) {
        return mapper.toDomainList(jpaRepository.findByCustomerId(customerId));
    }

    @Override
    public List<Conversation> findByAgentId(Long agentId) {
        return mapper.toDomainList(jpaRepository.findByAgentId(agentId));
    }

    @Override
    public List<Conversation> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public List<Conversation> findByCustomerIdAndStatus(Long customerId, String status) {
        return mapper.toDomainList(jpaRepository.findByCustomerIdAndStatus(customerId, status));
    }

    @Override
    public List<Conversation> findByAgentIdAndStatus(Long agentId, String status) {
        return mapper.toDomainList(jpaRepository.findByAgentIdAndStatus(agentId, status));
    }

    @Override
    public List<Conversation> findWaitingConversations() {
        return mapper.toDomainList(jpaRepository.findWaitingConversations());
    }

    @Override
    public List<Conversation> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }
}
