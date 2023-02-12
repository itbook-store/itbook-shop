package shop.itbook.itbookshop.productgroup.productinquiry.transfer;

import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;

/**
 * 상품의 엔티티와 dto 간의 변환을 작성한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryTransfer {

    public ProductInquiryTransfer() {
    }

    public static ProductInquiry dtoToEntity(ProductInquiryRequestDto requestDto) {
        return ProductInquiry
            .builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .isPublic(requestDto.getIsPublic())
            .build();
    }
}


