package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByToken(String token);

    List<SessionEntity> findByCustomerId(Long customerId);

    List<SessionEntity> findByStatus(String status);

    @Query("SELECT s FROM SessionEntity s WHERE s.status = 'ACTIVE' AND s.expiryAt > :currentTime")
    List<SessionEntity> findActiveSessions(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT s FROM SessionEntity s WHERE s.expiryAt < :currentTime AND s.status = 'ACTIVE'")
    List<SessionEntity> findExpiredSessions(@Param("currentTime") LocalDateTime currentTime);
}
