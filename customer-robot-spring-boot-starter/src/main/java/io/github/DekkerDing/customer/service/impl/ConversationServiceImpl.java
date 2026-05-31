package io.github.DekkerDing.customer.service.impl;

import io.github.DekkerDing.customer.domain.conversation.Conversation;
import io.github.DekkerDing.customer.repository.ConversationRepository;
import io.github.DekkerDing.customer.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    @Transactional
    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    @Transactional
    public Conversation updateConversation(Conversation conversation) {
        return conversationRepository.update(conversation);
    }

    @Override
    @Transactional
    public void deleteConversation(Long id) {
        conversationRepository.deleteById(id);
    }

    @Override
    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("对话不存在 / Conversation not found"));
    }

    @Override
    @Transactional
    public Conversation assignAgent(Long conversationId, Long agentId) {
        Conversation conversation = getConversationById(conversationId);
        conversation.assignAgent(agentId);
        return conversationRepository.update(conversation);
    }

    @Override
    @Transactional
    public Conversation closeConversation(Long id) {
        Conversation conversation = getConversationById(id);
        conversation.close();
        return conversationRepository.update(conversation);
    }

    @Override
    public List<Conversation> getCustomerConversations(Long customerId) {
        return conversationRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Conversation> getAgentConversations(Long agentId) {
        return conversationRepository.findByAgentId(agentId);
    }

    @Override
    public List<Conversation> getWaitingConversations() {
        return conversationRepository.findWaitingConversations();
    }
}
