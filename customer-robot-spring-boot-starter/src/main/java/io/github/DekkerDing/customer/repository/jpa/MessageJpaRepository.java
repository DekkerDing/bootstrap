package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findByConversationIdOrderByCreatedAtAsc(Long conversationId);

    List<MessageEntity> findBySenderId(Long senderId);

    List<MessageEntity> findByConversationIdAndIsRead(Long conversationId, Boolean isRead);

    @Query("SELECT COUNT(m) FROM MessageEntity m WHERE m.conversationId = :conversationId AND m.isRead = false")
    long countUnreadMessages(@Param("conversationId") Long conversationId);
}
