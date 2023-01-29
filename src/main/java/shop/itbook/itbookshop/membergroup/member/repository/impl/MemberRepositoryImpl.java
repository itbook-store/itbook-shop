package shop.itbook.itbookshop.membergroup.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberOauthLoginResponseDto;
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

    @Override
    public boolean existsByEmailAndIsSocial(String email) {
        return jpaQueryFactory.from(qmember)
            .where(qmember.email.eq(email).and(qmember.isSocial.eq(false)))
            .select(qmember.email).fetchFirst() != null;
    }

    @Override
    public boolean existsByMemberIdAndIsSocial(String memberId) {
        return jpaQueryFactory.from(qmember)
            .where(qmember.memberId.eq(memberId).and(qmember.isSocial.eq(true)))
            .select(qmember.memberId).fetchFirst() != null;

    }

    @Override
    public Optional<MemberOauthLoginResponseDto> findOauthInfoByMemberId(String memberId) {
        return Optional.ofNullable(jpaQueryFactory.select(
                Projections.constructor(MemberOauthLoginResponseDto.class, qmember.memberId,
                    qmember.password))
            .from(qmember)
            .where(qmember.memberId.eq(memberId)
                .and(qmember.isSocial.eq(true))).fetchOne());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberExceptPwdResponseDto> findMemberListByMemberId(String memberId) {
        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber,
                    qmember.email,
                    qmember.memberCreatedAt)).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.memberId.contains(memberId)).fetch();
    }

    @Override
    public List<MemberExceptPwdResponseDto> findMemberListByNickname(String nickname) {

        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber,
                    qmember.email,
                    qmember.memberCreatedAt)).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.nickname.contains(nickname)).fetch();
    }

    @Override
    public List<MemberExceptPwdResponseDto> findMemberListByName(String name) {

        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber,
                    qmember.email,
                    qmember.memberCreatedAt)).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.name.contains(name)).fetch();
    }

    @Override
    public List<MemberExceptPwdResponseDto> findMemberListByPhoneNumber(String phoneNumber) {
        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber,
                    qmember.email,
                    qmember.memberCreatedAt)).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.phoneNumber.contains(phoneNumber)).fetch();
    }

    @Override
    public List<MemberExceptPwdResponseDto> findMemberListBySearchWord(String searchWord) {
        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname, qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber,
                    qmember.email,
                    qmember.memberCreatedAt)).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.memberId.contains(searchWord).or(qmember.nickname.contains(searchWord))
                .or(qmember.name.contains(searchWord)).or(qmember.phoneNumber.contains(searchWord)))
            .fetch();
    }
}
