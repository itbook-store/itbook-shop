package shop.itbook.itbookshop.ordergroup.orderproduct.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문한 상품의 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderProductDetailResponseDto {
    private Long orderProductNo;
    private Long productNo;
    private String productName;
    private Integer count;
    private Long productPrice;
    private String fileThumbnailsUrl;
    private String couponName;
    private Long couponAmount;
    private Integer couponPercent;

    private Long fixedPrice;
    private Double discountPercent;
    private Long sellingPrice;

}
