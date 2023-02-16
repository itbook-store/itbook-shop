package shop.itbook.itbookshop.ordergroup.ordersheet.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 주문서 작성 시 필요한 정보를 반환하는 Dto.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderSheetResponseDto {
    private final List<ProductDetailsResponseDto> productDetailsResponseDtoList;
    private final List<MemberDestinationResponseDto> memberDestinationResponseDtoList;

    private final Long deliveryFee;
    private final Long memberPoint;

    /**
     * 주문서 Dto 생성자.
     *
     * @param productDetailsResponseDtoList    제품 상세 정보 Dto 리스트
     * @param memberDestinationResponseDtoList 회원 배송지 정보 Dto 리스트
     * @param deliveryFee                      배송비
     * @param memberPoint                      회원이 가지고 있는 포인트
     */
    @Builder
    public OrderSheetResponseDto(List<ProductDetailsResponseDto> productDetailsResponseDtoList,
                                 List<MemberDestinationResponseDto> memberDestinationResponseDtoList,
                                 Long deliveryFee, Long memberPoint) {
        this.productDetailsResponseDtoList = productDetailsResponseDtoList;
        this.memberDestinationResponseDtoList = memberDestinationResponseDtoList;
        this.deliveryFee = deliveryFee;
        this.memberPoint = memberPoint;
    }
}
