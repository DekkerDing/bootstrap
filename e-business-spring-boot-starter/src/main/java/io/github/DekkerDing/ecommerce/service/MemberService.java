package io.github.DekkerDing.ecommerce.service;

import io.github.DekkerDing.ecommerce.domain.member.Member;
import io.github.DekkerDing.ecommerce.domain.member.PointsLog;
import io.github.DekkerDing.ecommerce.repository.MemberRepository;
import io.github.DekkerDing.ecommerce.repository.PointsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PointsLogRepository pointsLogRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, PointsLogRepository pointsLogRepository) {
        this.memberRepository = memberRepository;
        this.pointsLogRepository = pointsLogRepository;
    }

    public Member getOrCreateMember(Long userId) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if (memberOpt.isPresent()) {
            return memberOpt.get();
        }
        Member newMember = new Member();
        newMember.setUserId(userId);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByUserId(Long userId) {
        return memberRepository.findByUserId(userId);
    }

    @Transactional
    public Member addPoints(Long userId, Integer points, String type, String description, Long referenceId, String referenceType) {
        Member member = getOrCreateMember(userId);
        Integer balanceBefore = member.getPoints();

        member.addPoints(points);

        PointsLog log = new PointsLog();
        log.setUserId(userId);
        log.setPoints(points);
        log.setBalanceBefore(balanceBefore);
        log.setBalanceAfter(member.getPoints());
        log.setType(type);
        log.setDescription(description);
        log.setReferenceId(referenceId);
        log.setReferenceType(referenceType);
        pointsLogRepository.save(log);

        return memberRepository.save(member);
    }

    @Transactional
    public Member deductPoints(Long userId, Integer points, String type, String description, Long referenceId, String referenceType) {
        Member member = getOrCreateMember(userId);
        Integer balanceBefore = member.getPoints();

        member.deductPoints(points);

        PointsLog log = new PointsLog();
        log.setUserId(userId);
        log.setPoints(-points);
        log.setBalanceBefore(balanceBefore);
        log.setBalanceAfter(member.getPoints());
        log.setType(type);
        log.setDescription(description);
        log.setReferenceId(referenceId);
        log.setReferenceType(referenceType);
        pointsLogRepository.save(log);

        return memberRepository.save(member);
    }

    @Transactional
    public Member addGrowthValue(Long userId, Integer value) {
        Member member = getOrCreateMember(userId);
        member.addGrowthValue(value);
        return memberRepository.save(member);
    }

    public boolean isExpired(Member member) {
        return member.isExpired();
    }

    public List<PointsLog> getPointsLogs(Long userId) {
        return pointsLogRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
