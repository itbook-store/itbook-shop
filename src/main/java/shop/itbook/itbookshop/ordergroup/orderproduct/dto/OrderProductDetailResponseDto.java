package shop.itbook.itbookshop.ordergroup.orderproduct.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 주문한 상품의 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
public class OrderProductDetailResponseDto {
    private Long orderProductNo;
    private String productName;
    private Integer count;
    private Long productPrice;
    private String fileThumbnailsUrl;
}
