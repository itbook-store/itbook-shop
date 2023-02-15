package shop.itbook.itbookshop.ordergroup.ordersheet.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 주문서 작성 시 필요한 정보를 반환하는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderSheetResponseDto {
    private final List<ProductDetailsResponseDto> productDetailsResponseDtoList;
    private final List<MemberDestinationResponseDto> memberDestinationResponseDtoList;

    @Setter
    private Long memberPoint;

    public OrderSheetResponseDto(List<ProductDetailsResponseDto> productDetailsResponseDtoList,
                                 List<MemberDestinationResponseDto> memberDestinationResponseDtoList) {
        this.productDetailsResponseDtoList = productDetailsResponseDtoList;
        this.memberDestinationResponseDtoList = memberDestinationResponseDtoList;
    }
}
