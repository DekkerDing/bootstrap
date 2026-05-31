package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.dialogue.DialogueLog;
import io.github.DekkerDing.customer.repository.DialogueLogRepository;
import io.github.DekkerDing.customer.repository.jpa.DialogueLogJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.DialogueLogEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.DialogueLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DialogueLogRepositoryImpl implements DialogueLogRepository {
    private final DialogueLogJpaRepository dialogueLogJpaRepository;
    private final DialogueLogMapper dialogueLogMapper;

    @Autowired
    public DialogueLogRepositoryImpl(DialogueLogJpaRepository dialogueLogJpaRepository,
                                    DialogueLogMapper dialogueLogMapper) {
        this.dialogueLogJpaRepository = dialogueLogJpaRepository;
        this.dialogueLogMapper = dialogueLogMapper;
    }

    @Override
    public DialogueLog save(DialogueLog dialogueLog) {
        DialogueLogEntity entity = dialogueLogMapper.toEntity(dialogueLog);
        DialogueLogEntity savedEntity = dialogueLogJpaRepository.save(entity);
        return dialogueLogMapper.toDomain(savedEntity);
    }

    @Override
    public List<DialogueLog> findBySessionId(Long sessionId) {
        return dialogueLogMapper.toDomainList(
                dialogueLogJpaRepository.findBySessionIdOrderByCreatedAtAsc(sessionId)
        );
    }

    @Override
    public List<DialogueLog> findByUserId(Long userId) {
        return dialogueLogMapper.toDomainList(
                dialogueLogJpaRepository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }
}
