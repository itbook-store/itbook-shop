package shop.itbook.itbookshop.ordergroup.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 등록 성공 후 값을 반환하기 위한 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Setter
@Getter
public class OrderPaymentDto {
    private Long orderNo;
    private String orderId;
    private String orderName;
    private Long amount;
    private String successUrl;
    private String failUrl;


    /**
     * 주문 테이블에 값 등록 성공 후 결제를 위한 정보를 반환합니다.
     *
     * @param orderNo    등록된 주문 값의 pk.
     * @param orderId    결제 요청을 위한 주문 id (ex. dkfi3-dfasdfldk-34234)
     * @param orderName  결제 요청을 위한 주문 이름 (ex. 좋은 생각 1월호 외 2권)
     * @param amount     결제 요청할 상품들의 총 가격
     * @param successUrl the success url
     * @param failUrl    the fail url
     */
    @Builder
    public OrderPaymentDto(Long orderNo, String orderId, String orderName, Long amount,
                           String successUrl, String failUrl) {
        this.orderNo = orderNo;
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }
}
