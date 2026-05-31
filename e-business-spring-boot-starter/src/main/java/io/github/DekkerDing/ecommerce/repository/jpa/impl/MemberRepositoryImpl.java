package io.github.DekkerDing.ecommerce.repository.jpa.impl;

import io.github.DekkerDing.ecommerce.domain.member.Member;
import io.github.DekkerDing.ecommerce.repository.MemberRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.MemberJpaRepository;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.MemberEntity;
import io.github.DekkerDing.ecommerce.repository.jpa.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository,
                               MemberMapper memberMapper) {
        this.memberJpaRepository = memberJpaRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id)
                .map(memberMapper::toDomain);
    }

    @Override
    public Optional<Member> findByUserId(Long userId) {
        return memberJpaRepository.findByUserId(userId)
                .map(memberMapper::toDomain);
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = memberMapper.toEntity(member);
        MemberEntity savedEntity = memberJpaRepository.save(entity);
        return memberMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return memberJpaRepository.existsByUserId(userId);
    }
}
