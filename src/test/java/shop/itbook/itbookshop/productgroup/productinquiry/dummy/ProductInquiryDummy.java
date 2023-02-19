package shop.itbook.itbookshop.productgroup.productinquiry.dummy;

import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;

/**
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryDummy {

    public static ProductInquiry getProductInquiry() {

        return ProductInquiry
            .builder()
            .title("문의합니다.")
            .content("문의 내용_1")
            .isPublic(true)
            .isReplied(false)
            .isDeleted(false)
            .build();
    }
}
