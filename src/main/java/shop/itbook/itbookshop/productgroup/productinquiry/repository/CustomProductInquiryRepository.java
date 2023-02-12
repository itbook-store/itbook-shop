package shop.itbook.itbookshop.productgroup.productinquiry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;

/**
 * 커스텀 상품문의 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomProductInquiryRepository {

    /**
     * 모든 상품 문의 리스트를 불러옵니다.
     *
     * @return 페이지네이션 처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ProductInquiryResponseDto> findProductInquiryList(Pageable pageable);
}
