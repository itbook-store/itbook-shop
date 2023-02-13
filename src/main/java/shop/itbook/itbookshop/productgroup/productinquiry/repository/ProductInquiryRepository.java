package shop.itbook.itbookshop.productgroup.productinquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;

/**
 * 상품문의 레포지토리 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryRepository
    extends JpaRepository<ProductInquiry, Long>, CustomProductInquiryRepository {
}
