package shop.itbook.itbookshop.productgroup.producttyperegistration.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeRegistrationAdminService {
    List<FindProductTypeResponseDto> findProductType(Long productNo);

}
