package io.github.DekkerDing.customer.api;

import io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase;
import io.github.DekkerDing.customer.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库 API 控制器
 * Knowledge Base Controller
 */
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    @Autowired
    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    /**
     * 创建知识
     * Create knowledge
     */
    @PostMapping
    public ResponseEntity<ApiResponse<KnowledgeBase>> createKnowledge(@RequestBody KnowledgeBase knowledge) {
        KnowledgeBase created = knowledgeBaseService.createKnowledge(knowledge);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    /**
     * 获取知识详情
     * Get knowledge details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KnowledgeBase>> getKnowledge(@PathVariable Long id) {
        return knowledgeBaseService.findById(id)
                .map(knowledge -> ResponseEntity.ok(ApiResponse.success(knowledge)))
                .orElse(ResponseEntity.ok(ApiResponse.notFound("知识不存在 / Knowledge not found")));
    }

    /**
     * 获取活动知识列表
     * Get active knowledge list
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<KnowledgeBase>>> getActiveKnowledge() {
        List<KnowledgeBase> knowledgeList = knowledgeBaseService.findActive();
        return ResponseEntity.ok(ApiResponse.success(knowledgeList));
    }

    /**
     * 按分类获取知识
     * Get knowledge by category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<KnowledgeBase>>> getByCategory(@PathVariable Long categoryId) {
        List<KnowledgeBase> knowledgeList = knowledgeBaseService.findByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(knowledgeList));
    }

    /**
     * 搜索知识
     * Search knowledge
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<KnowledgeBase>>> search(@RequestParam String keyword) {
        List<KnowledgeBase> knowledgeList = knowledgeBaseService.search(keyword);
        return ResponseEntity.ok(ApiResponse.success(knowledgeList));
    }

    /**
     * 更新知识
     * Update knowledge
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KnowledgeBase>> updateKnowledge(
            @PathVariable Long id,
            @RequestBody KnowledgeBase knowledge) {
        knowledge.setId(id);
        KnowledgeBase updated = knowledgeBaseService.updateKnowledge(knowledge);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    /**
     * 删除知识（软删除）
     * Delete knowledge (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteKnowledge(@PathVariable Long id) {
        knowledgeBaseService.deleteKnowledge(id);
        return ResponseEntity.ok(ApiResponse.<Void>success("知识已删除 / Knowledge deleted", null));
    }
}
