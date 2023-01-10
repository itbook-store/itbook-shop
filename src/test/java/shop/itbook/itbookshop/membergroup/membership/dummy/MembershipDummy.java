package shop.itbook.itbookshop.membergroup.membership.dummy;

import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * MembershipDummy 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MembershipDummy {
    public static Membership getMembership() {

        return new Membership("white", 100_000L, 10_000L);
    }
}
