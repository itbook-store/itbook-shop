package shop.itbook.itbookshop.membergroup.member.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * MemberDummy 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberDummy {

    public static Member getMember() {
        Membership membership = MembershipDummy.getMembership();
        MemberStatus memberStatus = MemberStatusDummy.getMemberStatus();

        return new Member(membership, memberStatus, "user4", "유저4", "김유리", true,
            LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-4000-0000", "user4@test1.com",
            LocalDateTime.now());
    }

}
