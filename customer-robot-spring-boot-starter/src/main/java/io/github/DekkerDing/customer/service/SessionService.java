package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.session.Session;

import java.util.Optional;

/**
 * 会话服务接口
 * Session Service Interface
 */
public interface SessionService {

    Session createSession(Session session);

    void logout(String token);

    Session getSessionByToken(String token);

    boolean validateSession(String token);

    void expireOldSessions();
}
