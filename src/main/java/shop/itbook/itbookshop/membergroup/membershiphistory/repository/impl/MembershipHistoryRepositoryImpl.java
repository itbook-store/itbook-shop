package shop.itbook.itbookshop.membergroup.membershiphistory.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.membergroup.membership.entity.QMembership;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.MembershipHistory;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.QMembershipHistory;
import shop.itbook.itbookshop.membergroup.membershiphistory.repository.CustomMembershipHistoryRepository;

/**
 * @author 노수연
 * @since 1.0
 */
public class MembershipHistoryRepositoryImpl extends QuerydslRepositorySupport
    implements CustomMembershipHistoryRepository {

    public MembershipHistoryRepositoryImpl() {
        super(MembershipHistory.class);
    }

    QMembershipHistory qMembershipHistory = QMembershipHistory.membershipHistory;
    QMembership qMembership = QMembership.membership;
    QMember qMember = QMember.member;

    @Override
    public List<MembershipHistoryResponseDto> findByMemberId(String memberId) {

        return from(qMembershipHistory)
            .innerJoin(qMembership)
            .on(qMembershipHistory.membership.membershipNo.eq(qMembership.membershipNo))
            .innerJoin(qMember)
            .on(qMembershipHistory.member.memberNo.eq(qMember.memberNo))
            .where(qMember.memberId.eq(memberId))
            .select(Projections.constructor(MembershipHistoryResponseDto.class,
                qMembershipHistory.membershipHistoryNo, qMembershipHistory.monthlyUsageAmount,
                qMembershipHistory.membershipHistoryCreatedAt, qMember.memberNo,
                qMembership.membershipNo, qMembership.membershipGrade,
                qMember.memberStatus.memberStatusNo, qMember.nickname, qMember.name, qMember.isMan,
                qMember.birth, qMember.phoneNumber, qMember.email, qMember.memberCreatedAt))
            .fetch();
    }
}
