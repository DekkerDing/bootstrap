package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.session.Session;
import io.github.DekkerDing.customer.repository.SessionRepository;
import io.github.DekkerDing.customer.repository.jpa.SessionJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.SessionEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(prefix = "customer-robot.persistence", name = "type", havingValue = "jpa", matchIfMissing = true)
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaRepository jpaRepository;
    private final SessionMapper mapper;

    @Autowired
    public SessionRepositoryImpl(SessionJpaRepository jpaRepository, SessionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Session save(Session session) {
        SessionEntity entity = mapper.toEntity(session);
        SessionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Session update(Session session) {
        SessionEntity entity = mapper.toEntity(session);
        entity.setId(session.getId());
        SessionEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        jpaRepository.findByToken(token).ifPresent(entity -> jpaRepository.deleteById(entity.getId()));
    }

    @Override
    public Optional<Session> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Session> findByToken(String token) {
        return jpaRepository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public List<Session> findByCustomerId(Long customerId) {
        return mapper.toDomainList(jpaRepository.findByCustomerId(customerId));
    }

    @Override
    public List<Session> findByStatus(String status) {
        return mapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public List<Session> findActiveSessions() {
        return mapper.toDomainList(jpaRepository.findActiveSessions(LocalDateTime.now()));
    }

    @Override
    public List<Session> findExpiredSessions() {
        return mapper.toDomainList(jpaRepository.findExpiredSessions(LocalDateTime.now()));
    }

    @Override
    public List<Session> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    @Transactional
    public void expireSession(Long sessionId) {
        SessionEntity entity = jpaRepository.findById(sessionId).orElse(null);
        if (entity != null) {
            entity.setStatus("EXPIRED");
            jpaRepository.save(entity);
        }
    }
}
