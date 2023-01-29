package shop.itbook.itbookshop.ordergroup.ordersheet.dto.response;

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
public class OrderSheetResponseDto {
    private List<OrderSheetProductResponseDto> orderSheetProductResponseDtoList;
    private List<MemberDestinationResponseDto> memberDestinationResponseDtoList;
}
