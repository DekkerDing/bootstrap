package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.session.Session;

import java.util.List;
import java.util.Optional;

/**
 * 会话仓储接口
 * Session Repository Interface
 */
public interface SessionRepository {

    Session save(Session session);

    Session update(Session session);

    void deleteById(Long id);

    void deleteByToken(String token);

    Optional<Session> findById(Long id);

    Optional<Session> findByToken(String token);

    List<Session> findByCustomerId(Long customerId);

    List<Session> findByStatus(String status);

    List<Session> findActiveSessions();

    List<Session> findExpiredSessions();

    List<Session> findAll();

    void expireSession(Long sessionId);
}
