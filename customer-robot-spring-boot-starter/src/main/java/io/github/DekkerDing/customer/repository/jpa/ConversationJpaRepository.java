package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationJpaRepository extends JpaRepository<ConversationEntity, Long> {

    Optional<ConversationEntity> findByConversationNo(String conversationNo);

    List<ConversationEntity> findByCustomerId(Long customerId);

    List<ConversationEntity> findByAgentId(Long agentId);

    List<ConversationEntity> findByStatus(String status);

    List<ConversationEntity> findByCustomerIdAndStatus(Long customerId, String status);

    List<ConversationEntity> findByAgentIdAndStatus(Long agentId, String status);

    @Query("SELECT c FROM ConversationEntity c WHERE c.status = 'WAITING' ORDER BY c.createdAt ASC")
    List<ConversationEntity> findWaitingConversations();
}
