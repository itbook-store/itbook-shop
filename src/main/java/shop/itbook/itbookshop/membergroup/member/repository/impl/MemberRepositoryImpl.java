package shop.itbook.itbookshop.membergroup.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberOauthLoginResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.membergroup.member.repository.CustomMemberRepository;
import shop.itbook.itbookshop.membergroup.membership.entity.QMembership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.QMemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatushistory.entity.QMemberStatusHistory;

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

    QMemberStatusHistory qMemberStatusHistory = QMemberStatusHistory.memberStatusHistory;

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
    public Page<MemberExceptPwdResponseDto> findMemberList(Pageable pageable) {

        JPQLQuery<MemberExceptPwdResponseDto> jpqlQuery = jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus);

        List<MemberExceptPwdResponseDto> memberList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(memberList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findNormalMemberList(Pageable pageable) {
        JPQLQuery<MemberExceptPwdResponseDto> jpqlQuery = jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmemberStatus.memberStatusEnum.stringValue().eq("정상회원"));

        List<MemberExceptPwdResponseDto> memberList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(memberList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findBlockMemberList(Pageable pageable) {
        JPQLQuery<MemberExceptPwdResponseDto> jpqlQuery = jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmemberStatus.memberStatusEnum.stringValue().eq("차단회원"));

        List<MemberExceptPwdResponseDto> memberList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(memberList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findWithdrawMemberList(Pageable pageable) {
        JPQLQuery<MemberExceptPwdResponseDto> jpqlQuery = jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdResponseDto.class, qmember.memberNo,
                    qmember.memberId,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmemberStatus.memberStatusEnum.stringValue().eq("탈퇴회원"));

        List<MemberExceptPwdResponseDto> memberList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(memberList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public boolean existsByEmailAndIsSocial(String email) {
        return jpaQueryFactory.from(qmember)
            .where(qmember.email.eq(email).and(qmember.isSocial.eq(false)))
            .select(qmember.email).fetchFirst() != null;
    }

    @Override
    public boolean existsByMemberIdAndIsSocial(String email) {
        return jpaQueryFactory.from(qmember)
            .where(qmember.memberId.eq(email).and(qmember.isSocial.eq(true)))
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

    @Override
    public MemberExceptPwdBlockResponseDto findBlockMemberByMemberId(String memberId) {
        return jpaQueryFactory.select(
                Projections.constructor(MemberExceptPwdBlockResponseDto.class, qmember.memberNo,
                    qmember.memberId, qmembership.membershipGrade,
                    qmemberStatus.memberStatusEnum.stringValue(), qmember.nickname, qmember.name,
                    qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt, qMemberStatusHistory.statusChangedReason,
                    qMemberStatusHistory.memberStatusHistoryCreatedAt)).from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .join(qMemberStatusHistory)
            .on(qmember.memberNo.eq(qMemberStatusHistory.member.memberNo))
            .where(Expressions.list(qmember.memberNo,
                qMemberStatusHistory.memberStatusHistoryCreatedAt).in(
                JPAExpressions.select(qmember.memberNo,
                        qMemberStatusHistory.memberStatusHistoryCreatedAt.max())
                    .from(qmember)
                    .join(qmember.memberStatus, qmemberStatus)
                    .join(qMemberStatusHistory)
                    .on(qmember.memberNo.eq(qMemberStatusHistory.member.memberNo))
                    .where(qmemberStatus.memberStatusEnum.stringValue().eq("차단회원")
                        .and(qmember.memberId.eq(memberId)))
                    .groupBy(qmember.memberNo))
            ).orderBy(qmember.memberNo.desc()).fetchOne();
    }

    @Override
    public MemberCountResponseDto MemberCountBy() {
        return jpaQueryFactory.select(
            Projections.constructor(MemberCountResponseDto.class, qmember.count())
        ).from(qmember).fetchFirst();
    }

    @Override
    public MemberCountResponseDto blockMemberCountBy() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberCountResponseDto.class, qmember.count())
            ).from(qmember).join(qmember.memberStatus, qmemberStatus)
            .where(qmember.memberStatus.memberStatusEnum.stringValue().eq("차단회원"))
            .fetchOne();
    }

    @Override
    public MemberCountResponseDto withdrawMemberCountBy() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberCountResponseDto.class, qmember.count())
            ).from(qmember).join(qmember.memberStatus, qmemberStatus)
            .where(qmember.memberStatus.memberStatusEnum.stringValue().eq("탈퇴회원"))
            .fetchOne();
    }
}
