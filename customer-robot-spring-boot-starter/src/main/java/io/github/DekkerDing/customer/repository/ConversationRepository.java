package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.conversation.Conversation;

import java.util.List;
import java.util.Optional;

/**
 * 对话仓储接口
 * Conversation Repository Interface
 */
public interface ConversationRepository {

    Conversation save(Conversation conversation);

    Conversation update(Conversation conversation);

    void deleteById(Long id);

    Optional<Conversation> findById(Long id);

    Optional<Conversation> findByConversationNo(String conversationNo);

    List<Conversation> findByCustomerId(Long customerId);

    List<Conversation> findByAgentId(Long agentId);

    List<Conversation> findByStatus(String status);

    List<Conversation> findByCustomerIdAndStatus(Long customerId, String status);

    List<Conversation> findByAgentIdAndStatus(Long agentId, String status);

    List<Conversation> findWaitingConversations();

    List<Conversation> findAll();
}
