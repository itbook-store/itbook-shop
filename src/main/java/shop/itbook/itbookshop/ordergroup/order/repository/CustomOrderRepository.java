package shop.itbook.itbookshop.ordergroup.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListViewResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;

/**
 * 주문 엔티티 관련 쿼리 dsl 을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomOrderRepository {

    Page<OrderListViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                  Long memberNo);
}
