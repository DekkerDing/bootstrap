package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.DialogueLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueLogJpaRepository extends JpaRepository<DialogueLogEntity, Long> {
    List<DialogueLogEntity> findBySessionIdOrderByCreatedAtAsc(Long sessionId);
    List<DialogueLogEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}
