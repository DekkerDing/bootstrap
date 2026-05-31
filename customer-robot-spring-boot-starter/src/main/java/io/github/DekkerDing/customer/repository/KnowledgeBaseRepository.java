package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase;
import java.util.List;
import java.util.Optional;

public interface KnowledgeBaseRepository {
    Optional<KnowledgeBase> findById(Long id);
    List<KnowledgeBase> findByActiveStatus();
    List<KnowledgeBase> findByCategory(Long categoryId);
    List<KnowledgeBase> searchByKeyword(String keyword);
    KnowledgeBase save(KnowledgeBase knowledgeBase);
}
