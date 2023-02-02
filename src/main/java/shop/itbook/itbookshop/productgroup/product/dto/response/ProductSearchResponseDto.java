package shop.itbook.itbookshop.productgroup.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 검색 결과값을 반환하는 DTO 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductSearchResponseDto {

    private Long productNo;
    private String name;
    private String simpleDescription;
    private String detailsDescription;
    private Integer stock;
    private boolean isSelled;
    private boolean isDeleted;
    private String thumbnailUrl;
    private Long fixedPrice;
    private Integer increasePointPercent;
    private Double discountPercent;
    private Long rawPrice;
    private Long selledPrice;
}
