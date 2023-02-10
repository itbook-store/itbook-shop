package shop.itbook.itbookshop.membergroup.memberrole.dummy;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * @author 강명관
 * @since 1.0
 */
public class MemberRoleDummy {

    private MemberRoleDummy() {

    }

    public static MemberRole getMemberRole(Member member, Role role) {
        return new MemberRole(member, role);
    }
}
