package io.github.DekkerDing.ecommerce.repository;

import io.github.DekkerDing.ecommerce.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(Long id);
    Optional<Member> findByUserId(Long userId);
    Member save(Member member);
    boolean existsByUserId(Long userId);
}
