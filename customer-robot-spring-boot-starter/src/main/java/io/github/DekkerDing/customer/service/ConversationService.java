package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.conversation.Conversation;

import java.util.List;

/**
 * 对话服务接口
 * Conversation Service Interface
 */
public interface ConversationService {

    Conversation createConversation(Conversation conversation);

    Conversation updateConversation(Conversation conversation);

    void deleteConversation(Long id);

    Conversation getConversationById(Long id);

    Conversation assignAgent(Long conversationId, Long agentId);

    Conversation closeConversation(Long id);

    List<Conversation> getCustomerConversations(Long customerId);

    List<Conversation> getAgentConversations(Long agentId);

    List<Conversation> getWaitingConversations();
}
