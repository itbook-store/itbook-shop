package shop.itbook.itbookshop.membergroup.memberdestination.dummy;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * @author 정재원
 * @since 1.0
 */
public class MemberDestinationDummy {
    public static MemberDestination createMemberDestination(Member member) {
        return MemberDestination.builder()
            .member(member)
            .recipientName("테스트 수령인")
            .recipientPhoneNumber("010-xxxx-xxxx")
            .postcode(12345)
            .roadNameAddress("테스트 도로명주소")
            .recipientAddressDetails("101동 1103호")
            .build();
    }
}