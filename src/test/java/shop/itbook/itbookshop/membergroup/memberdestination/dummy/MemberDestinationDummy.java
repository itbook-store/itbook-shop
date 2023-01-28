package shop.itbook.itbookshop.membergroup.memberdestination.dummy;

import shop.itbook.itbookshop.deliverygroup.deliverydestination.dummy.DeliveryDestinationDummy;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * @author 정재원
 * @since 1.0
 */
public class MemberDestinationDummy {
    public static MemberDestination getMemberDestination() {
        return MemberDestination.builder()
            .deliveryDestination(DeliveryDestinationDummy.getDeliveryDestination())
            .member(MemberDummy.getMember1())
            .recipientName("테스트 수령인")
            .recipientPhoneNumber("010-xxxx-xxxx")
            .recipientAddressDetails("303호")
            .build();
    }
}