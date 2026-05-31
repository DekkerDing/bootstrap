package io.github.DekkerDing.ecommerce.repository.jpa;

import io.github.DekkerDing.ecommerce.repository.jpa.entity.PointsLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsLogJpaRepository extends JpaRepository<PointsLogEntity, Long> {
    List<PointsLogEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<PointsLogEntity> findByReferenceIdAndReferenceType(Long referenceId, String referenceType);
}
