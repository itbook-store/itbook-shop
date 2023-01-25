package shop.itbook.itbookshop.membergroup.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
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

    @Override
    public Optional<MemberResponseProjectionDto> querydslFindByMemberId(String memberId) {

        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberResponseProjectionDto.class, qmember.memberNo,
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

    @Override
    public Optional<MemberResponseDto> querydslFindByMemberIdAllInfo(String memberId) {
        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberResponseDto.class, qmember.memberNo, qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.password,
                    qmember.phoneNumber, qmember.email, qmember.memberCreatedAt
                )).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus).where(qmember.memberId.eq(memberId))
            .fetchOne());
    }

    @Override
    public Optional<Member> querydslFindByMemberIdToMember(String memberId) {


        return Optional.of(jpaQueryFactory.select(qmember).from(qmember)
            .innerJoin(qmember.membership, qmembership)
            .fetchJoin()
            .innerJoin(qmember.memberStatus, qmemberStatus)
            .fetchJoin()
            .where(qmember.memberId.eq(memberId))
            .fetchOne());
    }

    @Override
    public List<MemberResponseProjectionDto> querydslFindAll() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberResponseProjectionDto.class, qmember.memberNo,
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
    public MemberAuthResponseDto findAuthInfoByMemberId(String memberId) {
        return jpaQueryFactory.from(qmember)
            .where(qmember.memberId.eq(memberId))
            .select(Projections.constructor(
                MemberAuthResponseDto.class,
                qmember.memberNo,
                qmember.memberId,
                qmember.password
            )).fetchOne();
    }
}
