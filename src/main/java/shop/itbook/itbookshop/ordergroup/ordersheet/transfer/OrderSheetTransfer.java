package shop.itbook.itbookshop.ordergroup.ordersheet.transfer;

import java.util.List;
import java.util.stream.Collectors;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 주문서의 entity 와 dto 간 변화를 담당하는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderSheetTransfer {

    /**
     * 상품 정보 Dto 의 리스트와 회원 배송지 정보 Dto 를 취합하여 주문서 Dto 를 만들어서 반환합니다.
     *
     * @param productDetailsResponseDtoList    상품 상세 정보가 담긴 dto 리스트
     * @param memberDestinationResponseDtoList 주문서에 담길 회원 배송지 정보 dto 리스트
     * @return 상품 정보와 회원 배송지 정보를 합친 주문서 dto
     * @author 정재원
     */
    public static OrderSheetResponseDto createOrderSheetResponseDto(
        List<ProductDetailsResponseDto> productDetailsResponseDtoList,
        List<MemberDestinationResponseDto> memberDestinationResponseDtoList,
        Long memberPoint) {

        long deliveryFee = 3000L;
        long amount = 0;

        for (ProductDetailsResponseDto product : productDetailsResponseDtoList) {
            amount += product.getSelledPrice();
        }

        if (amount >= 20000L) {
            deliveryFee = 0L;
        }

        return OrderSheetResponseDto.builder()
            .productDetailsResponseDtoList(productDetailsResponseDtoList)
            .memberDestinationResponseDtoList(memberDestinationResponseDtoList)
            .deliveryFee(deliveryFee)
            .memberPoint(memberPoint)
            .build();
    }
}
