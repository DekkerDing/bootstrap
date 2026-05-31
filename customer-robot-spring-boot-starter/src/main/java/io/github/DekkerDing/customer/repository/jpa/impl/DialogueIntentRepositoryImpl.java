package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.dialogue.DialogueIntent;
import io.github.DekkerDing.customer.repository.DialogueIntentRepository;
import io.github.DekkerDing.customer.repository.jpa.DialogueIntentJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.DialogueIntentEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.DialogueIntentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DialogueIntentRepositoryImpl implements DialogueIntentRepository {
    private final DialogueIntentJpaRepository dialogueIntentJpaRepository;
    private final DialogueIntentMapper dialogueIntentMapper;

    @Autowired
    public DialogueIntentRepositoryImpl(DialogueIntentJpaRepository dialogueIntentJpaRepository,
                                       DialogueIntentMapper dialogueIntentMapper) {
        this.dialogueIntentJpaRepository = dialogueIntentJpaRepository;
        this.dialogueIntentMapper = dialogueIntentMapper;
    }

    @Override
    public Optional<DialogueIntent> findById(Long id) {
        return dialogueIntentJpaRepository.findById(id)
                .map(dialogueIntentMapper::toDomain);
    }

    @Override
    public Optional<DialogueIntent> findByIntentCode(String intentCode) {
        return Optional.ofNullable(dialogueIntentJpaRepository.findByIntentCode(intentCode))
                .map(dialogueIntentMapper::toDomain);
    }

    @Override
    public List<DialogueIntent> findByActiveStatus() {
        return dialogueIntentMapper.toDomainList(
                dialogueIntentJpaRepository.findByStatusOrderByPriorityDesc("ACTIVE")
        );
    }

    @Override
    public List<DialogueIntent> findAll() {
        return dialogueIntentMapper.toDomainList(dialogueIntentJpaRepository.findAll());
    }

    @Override
    public DialogueIntent save(DialogueIntent intent) {
        DialogueIntentEntity entity = dialogueIntentMapper.toEntity(intent);
        DialogueIntentEntity savedEntity = dialogueIntentJpaRepository.save(entity);
        return dialogueIntentMapper.toDomain(savedEntity);
    }
}
