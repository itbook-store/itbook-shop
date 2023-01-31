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

    public static MemberStatus getNormalMemberStatus() {

        return MemberStatus.builder().memberStatusEnum(MemberStatusEnum.NORMAL)
            .build();
    }

    public static MemberStatus getBlockMemberStatus() {

        return MemberStatus.builder().memberStatusNo(2).memberStatusEnum(MemberStatusEnum.BLOCK)
            .build();
    }

    public static MemberStatus getWithdrawMemberStatus() {

        return MemberStatus.builder().memberStatusNo(3).memberStatusEnum(MemberStatusEnum.WITHDRAW)
            .build();
    }
}
