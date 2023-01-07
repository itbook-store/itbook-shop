package shop.itbook.itbookshop.productgroup.product.service;

import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품 crud 기능의 서비스 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {
    @Transactional
    AddProductResponseDto addProduct(AddProductRequestDto requestDto);

    @Transactional
    void modifyProduct(Long productNo, ModifyProductRequestDto requestDto);

    @Transactional
    Boolean removeProduct(Long productNo);

    Product findProduct(Long productNo);
}
