package shop.itbook.itbookshop.ordergroup.orderproduct.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;

/**
 * 주문 엔티티 관련 쿼리 dsl 을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomOrderProductRepository {
    List<OrderProductDetailResponseDto> findOrderProductsByOrderNo(Long orderNo);
}
