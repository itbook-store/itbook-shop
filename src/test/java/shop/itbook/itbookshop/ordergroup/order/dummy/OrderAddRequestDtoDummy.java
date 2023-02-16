package shop.itbook.itbookshop.ordergroup.order.dummy;

import java.time.LocalDate;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderAddRequestDtoDummy {

    public static OrderAddRequestDto getDummy() {
        OrderAddRequestDto orderAddRequestDtoDummy = new OrderAddRequestDto();

        orderAddRequestDtoDummy.setProductDetailsDtoList(ProductDetailsDtoDummy.getDummyList());
        orderAddRequestDtoDummy.setSelectedDeliveryDate(LocalDate.now());
        orderAddRequestDtoDummy.setRecipientName("테스트 수령인");
        orderAddRequestDtoDummy.setRecipientPhoneNumber("010-0000-0000");
        orderAddRequestDtoDummy.setPostcode(12345);
        orderAddRequestDtoDummy.setRoadNameAddress("테스트 도로명");
        orderAddRequestDtoDummy.setRecipientAddressDetails("테스트 상세주소");
        orderAddRequestDtoDummy.setOrderTotalCouponNo(999L);

        // 결제 시 변동 가능
        orderAddRequestDtoDummy.setDeliveryFee(0L);
        orderAddRequestDtoDummy.setDecreasePoint(0L);

        // 구독 시 변동 가능
        orderAddRequestDtoDummy.setIsSubscription(false);
        orderAddRequestDtoDummy.setSubscriptionPeriod(0);

        return orderAddRequestDtoDummy;
    }

}
