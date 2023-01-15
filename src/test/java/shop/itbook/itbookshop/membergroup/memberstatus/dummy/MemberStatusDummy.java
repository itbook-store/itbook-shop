package shop.itbook.itbookshop.membergroup.memberstatus.dummy;

import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * MemberStatusDummy 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberStatusDummy {

    public static MemberStatus getMemberStatus() {

        return new MemberStatus(1, MemberStatusEnum.NORMAL);
    }
}
