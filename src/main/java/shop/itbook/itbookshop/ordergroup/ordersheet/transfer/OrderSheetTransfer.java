package shop.itbook.itbookshop.ordergroup.ordersheet.transfer;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetProductResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 주문서의 entity 와 dto 간 변화를 담당하는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderSheetTransfer {
    /**
     * 상품 엔티티에서 주문서 작성에 필요한 정보를 골라서 Dto 로 만든다.
     *
     * @param product 주문할 상품의 엔티티
     * @return 주문서 작성에 필요한 정보가 담긴 상품의 Dto
     * @author 정재원 *
     */
    public static OrderSheetProductResponseDto productEntityToOrderSheetResponseDto(
        Product product) {

        return new OrderSheetProductResponseDto(product.getName(), product.getFixedPrice(),
            product.getDiscountPercent());
    }

    /**
     * 상품 정보 Dto 의 리스트와 회원 배송지 정보 Dto 를 취합하여 주문서 Dto 를 만들어서 반환합니다.
     *
     * @param orderSheetProductResponseDtoList 주문서에 담길 상품 dto 리스트
     * @param memberDestinationResponseDtoList 주문서에 담길 회원 배송지 정보 dto 리스트
     * @return 상품 정보와 회원 배송지 정보를 합친 주문서 dto
     * @author 정재원 *
     */
    public static OrderSheetResponseDto createOrderSheetResponseDto(
        List<OrderSheetProductResponseDto> orderSheetProductResponseDtoList,
        List<MemberDestinationResponseDto> memberDestinationResponseDtoList) {

        return new OrderSheetResponseDto(orderSheetProductResponseDtoList,
            memberDestinationResponseDtoList);
    }
}
