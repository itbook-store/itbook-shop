package shop.itbook.itbookshop.membergroup.member.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
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
        Membership membership = new Membership("실버", 200_000L, 20_000L);
        MemberStatus memberStatus = new MemberStatus(MemberStatusEnum.NORMAL);

        Member member = new Member(membership, memberStatus, "user1", "유저1", "홍길동", true,
            LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1@test1.com");

        return member;
    }

}
