package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

@NoRepositoryBean
public interface ProductTypeRegistrationRepositoryCustom {

    Page<FindProductTypeResponseDto> findProductTypeListWithProductNo(Pageable pageable,
                                                                      Long productNo);

    Page<FindProductResponseDto> findProductListWithProductTypeNo(Pageable pageable,
                                                                  Integer productTypeNo,
                                                                  Boolean isExposed);
}
