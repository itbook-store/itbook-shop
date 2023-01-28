package shop.itbook.itbookshop.ordergroup.order.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;

/**
 * 주문서 작성 시 필요한 정보를 반환하는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderPaperResponseDto {
    // TODO: 2023/01/27 상품 응답 Dto

    private List<MemberDestinationResponseDto> memberDestinationResponseDtoList;
}
