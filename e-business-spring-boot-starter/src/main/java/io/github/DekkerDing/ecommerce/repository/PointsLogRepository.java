package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.member.PointsLog;

import java.util.List;

public interface PointsLogRepository {
    PointsLog save(PointsLog pointsLog);
    List<PointsLog> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<PointsLog> findByReferenceIdAndReferenceType(Long referenceId, String referenceType);
}
