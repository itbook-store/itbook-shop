package shop.itbook.itbookshop.membergroup.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.membergroup.member.repository.CustomMemberRepository;
import shop.itbook.itbookshop.membergroup.membership.entity.QMembership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.QMemberStatus;

/**
 * MemberRepositoryCustom 인터페이스를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */

public class MemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    QMember qmember = QMember.member;
    QMembership qmembership = QMembership.membership;
    QMemberStatus qmemberStatus = QMemberStatus.memberStatus;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MemberExceptPwdResponseDto> findByMemberId(String memberId) {

        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.memberId.eq(memberId))
            .fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MemberResponseDto> findByMemberIdAllInfo(String memberId) {
        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.password,
                    qmember.phoneNumber, qmember.email, qmember.memberCreatedAt
                )).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus).where(qmember.memberId.eq(memberId))
            .fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Member> findByMemberIdReceiveMember(String memberId) {


        return Optional.of(jpaQueryFactory.select(qmember).from(qmember)
            .innerJoin(qmember.membership, qmembership)
            .fetchJoin()
            .innerJoin(qmember.memberStatus, qmemberStatus)
            .fetchJoin()
            .where(qmember.memberId.eq(memberId))
            .fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberExceptPwdResponseDto> findMemberList() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MemberAuthInfoResponseDto> findAuthInfoByMemberId(String memberId) {
        return Optional.ofNullable(jpaQueryFactory.from(qmember)
            .where(qmember.memberId.eq(memberId))
            .select(Projections.constructor(
                MemberAuthInfoResponseDto.class,
                qmember.memberNo,
                qmember.memberId,
                qmember.password
            )).fetchOne());
    }
}
