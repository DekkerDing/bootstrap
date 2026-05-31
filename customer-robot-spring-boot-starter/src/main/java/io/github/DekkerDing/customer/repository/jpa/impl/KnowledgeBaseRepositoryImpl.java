package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase;
import io.github.DekkerDing.customer.repository.KnowledgeBaseRepository;
import io.github.DekkerDing.customer.repository.jpa.KnowledgeBaseJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.KnowledgeBaseEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.KnowledgeBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KnowledgeBaseRepositoryImpl implements KnowledgeBaseRepository {
    private final KnowledgeBaseJpaRepository knowledgeBaseJpaRepository;
    private final KnowledgeBaseMapper knowledgeBaseMapper;

    @Autowired
    public KnowledgeBaseRepositoryImpl(KnowledgeBaseJpaRepository knowledgeBaseJpaRepository,
                                       KnowledgeBaseMapper knowledgeBaseMapper) {
        this.knowledgeBaseJpaRepository = knowledgeBaseJpaRepository;
        this.knowledgeBaseMapper = knowledgeBaseMapper;
    }

    @Override
    public Optional<KnowledgeBase> findById(Long id) {
        return knowledgeBaseJpaRepository.findById(id)
                .map(knowledgeBaseMapper::toDomain);
    }

    @Override
    public List<KnowledgeBase> findByActiveStatus() {
        return knowledgeBaseMapper.toDomainList(
                knowledgeBaseJpaRepository.findByStatusOrderByPriorityDesc("ACTIVE")
        );
    }

    @Override
    public List<KnowledgeBase> findByCategory(Long categoryId) {
        return knowledgeBaseMapper.toDomainList(
                knowledgeBaseJpaRepository.findByStatusAndCategoryId("ACTIVE", categoryId)
        );
    }

    @Override
    public List<KnowledgeBase> searchByKeyword(String keyword) {
        return knowledgeBaseMapper.toDomainList(
                knowledgeBaseJpaRepository.searchByKeyword(keyword)
        );
    }

    @Override
    public KnowledgeBase save(KnowledgeBase knowledgeBase) {
        KnowledgeBaseEntity entity = knowledgeBaseMapper.toEntity(knowledgeBase);
        KnowledgeBaseEntity savedEntity = knowledgeBaseJpaRepository.save(entity);
        return knowledgeBaseMapper.toDomain(savedEntity);
    }
}
