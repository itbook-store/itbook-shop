package shop.itbook.itbookshop.coupongroup.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 페이지에 반환한 값을 담을 response dto 객체입니다.
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCouponSimpleListResponseDto {
    private Long couponIssueNo;
    private Long couponNo;
    private String name;
    private String code;
    private Long amount;
    private Integer percent;
}
