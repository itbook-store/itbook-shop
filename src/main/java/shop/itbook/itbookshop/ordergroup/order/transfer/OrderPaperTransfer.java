package shop.itbook.itbookshop.ordergroup.order.transfer;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaperResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 주문서의 entity 와 dto 간 변화를 담당하는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderPaperTransfer {
    public static OrderPaperResponseDto entityToOrderPaperResponseDto(Product product,
                                                                      List<MemberDestination> memberDestinationList) {

        // TODO: 2023/01/27 상품 엔티티에서 필요한 정보를 뽑아낸 Dto

        // TODO: 2023/01/27

        return new OrderPaperResponseDto(memberDestinationList);
    }
}
