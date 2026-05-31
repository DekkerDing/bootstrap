package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.KnowledgeBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeBaseJpaRepository extends JpaRepository<KnowledgeBaseEntity, Long> {
    List<KnowledgeBaseEntity> findByStatusAndCategoryId(String status, Long categoryId);
    List<KnowledgeBaseEntity> findByStatusOrderByPriorityDesc(String status);

    @Query("SELECT k FROM KnowledgeBaseEntity k WHERE k.status = 'ACTIVE' AND (LOWER(k.question) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(k.tags) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<KnowledgeBaseEntity> searchByKeyword(String keyword);
}
