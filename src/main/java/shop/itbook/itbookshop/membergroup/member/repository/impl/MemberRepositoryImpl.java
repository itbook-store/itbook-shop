package shop.itbook.itbookshop.membergroup.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
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
    public Optional<MemberResponseProjectionDto> querydslFindById(String id) {

        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberResponseProjectionDto.class, qmember.id,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.id.eq(id))
            .fetchOne());
    }

    /*@Override
    public Optional<MemberResponseDto> querydslFindByIdAllInfo(String id) {
        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberResponseDto.class, qmember.memberNo, qmembership,
                    qmemberStatus, qmember.id, qmember.nickname, qmember.name, qmember.isMan,
                    qmember.birth, qmember.password, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt)
            ).from(qmember).join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .where(qmember.id.eq(id))
            .fetch().get(0));
    }*/

    @Override
    public Optional<Member> querydslFindByIdToMember(String id) {
        return Optional.of(jpaQueryFactory.select(Projections.fields(Member.class,
            qmember.memberNo, qmembership, qmemberStatus, qmember.id, qmember.nickname,
            qmember.name, qmember.isMan,
            qmember.birth, qmember.password, qmember.phoneNumber, qmember.email,
            qmember.memberCreatedAt
        )).from(qmember).where(qmember.id.eq(id)).fetch().get(0));
    }

    @Override
    public List<MemberResponseProjectionDto> querydslFindAll() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberResponseProjectionDto.class, qmember.id,
                    qmembership.membershipGrade, qmemberStatus.memberStatusEnum.stringValue(),
                    qmember.nickname,
                    qmember.name, qmember.isMan, qmember.birth, qmember.phoneNumber, qmember.email,
                    qmember.memberCreatedAt))
            .from(qmember)
            .join(qmember.membership, qmembership)
            .join(qmember.memberStatus, qmemberStatus)
            .fetch();
    }
}
