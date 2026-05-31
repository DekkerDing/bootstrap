package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.DialogueIntentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueIntentJpaRepository extends JpaRepository<DialogueIntentEntity, Long> {
    List<DialogueIntentEntity> findByStatusOrderByPriorityDesc(String status);
    DialogueIntentEntity findByIntentCode(String intentCode);
    List<DialogueIntentEntity> findByStatusAndIntentCodeContaining(String status, String intentCode);
}
