package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase;
import io.github.DekkerDing.customer.repository.KnowledgeBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeBaseService {
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    @Autowired
    public KnowledgeBaseService(KnowledgeBaseRepository knowledgeBaseRepository) {
        this.knowledgeBaseRepository = knowledgeBaseRepository;
    }

    public KnowledgeBase createKnowledge(KnowledgeBase knowledge) {
        return knowledgeBaseRepository.save(knowledge);
    }

    public Optional<KnowledgeBase> findById(Long id) {
        return knowledgeBaseRepository.findById(id);
    }

    public List<KnowledgeBase> findActive() {
        return knowledgeBaseRepository.findByActiveStatus();
    }

    public List<KnowledgeBase> findByCategory(Long categoryId) {
        return knowledgeBaseRepository.findByCategory(categoryId);
    }

    public List<KnowledgeBase> search(String keyword) {
        return knowledgeBaseRepository.searchByKeyword(keyword);
    }

    public KnowledgeBase updateKnowledge(KnowledgeBase knowledge) {
        return knowledgeBaseRepository.save(knowledge);
    }

    public void deleteKnowledge(Long id) {
        Optional<KnowledgeBase> knowledgeOpt = knowledgeBaseRepository.findById(id);
        if (knowledgeOpt.isPresent()) {
            KnowledgeBase knowledge = knowledgeOpt.get();
            knowledge.setStatus("INACTIVE");
            knowledgeBaseRepository.save(knowledge);
        }
    }
}
