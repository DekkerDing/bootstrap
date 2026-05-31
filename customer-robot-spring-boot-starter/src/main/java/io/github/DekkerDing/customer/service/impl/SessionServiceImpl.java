package io.github.DekkerDing.customer.service.impl;

import io.github.DekkerDing.customer.domain.session.Session;
import io.github.DekkerDing.customer.repository.SessionRepository;
import io.github.DekkerDing.customer.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void logout(String token) {
        sessionRepository.deleteByToken(token);
    }

    @Override
    public Session getSessionByToken(String token) {
        return sessionRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在 / Session not found"));
    }

    @Override
    public boolean validateSession(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        return session.isPresent() && session.get().isActive();
    }

    @Override
    @Transactional
    public void expireOldSessions() {
        List<Session> expiredSessions = sessionRepository.findExpiredSessions();
        for (Session session : expiredSessions) {
            session.expire();
            sessionRepository.update(session);
        }
    }
}
