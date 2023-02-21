package shop.itbook.itbookshop.paymentgroup.easypay.transfer;

import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * 카드에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class EasypayTransfer {
    private EasypayTransfer() {
    }

    /**
     * dto로 넘어온 값을 상품 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param responseDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author
     */
    public static Easypay dtoToEntity(PaymentResponseDto.PaymentDataResponseDto responseDto) {
        PaymentResponseDto.EasyPayResponseDto
            easypayResponseDto = responseDto.getEasyPay();
        return new Easypay(null, easypayResponseDto.getProvider(),
            easypayResponseDto.getAmount(), easypayResponseDto.getDiscountAmount());
    }

}
