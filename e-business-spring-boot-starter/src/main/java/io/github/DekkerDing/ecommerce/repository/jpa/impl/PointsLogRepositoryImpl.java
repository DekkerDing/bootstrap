package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.member.PointsLog;
import io.github.DekkerDing.ecommerce.repository.PointsLogRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.PointsLogJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.PointsLogEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.PointsLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointsLogRepositoryImpl implements PointsLogRepository {
    private final PointsLogJpaRepository pointsLogJpaRepository;
    private final PointsLogMapper pointsLogMapper;

    @Autowired
    public PointsLogRepositoryImpl(PointsLogJpaRepository pointsLogJpaRepository,
                                   PointsLogMapper pointsLogMapper) {
        this.pointsLogJpaRepository = pointsLogJpaRepository;
        this.pointsLogMapper = pointsLogMapper;
    }

    @Override
    public PointsLog save(PointsLog pointsLog) {
        PointsLogEntity entity = pointsLogMapper.toEntity(pointsLog);
        PointsLogEntity savedEntity = pointsLogJpaRepository.save(entity);
        return pointsLogMapper.toDomain(savedEntity);
    }

    @Override
    public List<PointsLog> findByUserIdOrderByCreatedAtDesc(Long userId) {
        return pointsLogMapper.toDomainList(
                pointsLogJpaRepository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }

    @Override
    public List<PointsLog> findByReferenceIdAndReferenceType(Long referenceId, String referenceType) {
        return pointsLogMapper.toDomainList(
                pointsLogJpaRepository.findByReferenceIdAndReferenceType(referenceId, referenceType)
        );
    }
}
