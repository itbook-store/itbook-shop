package shop.itbook.itbookshop.productgroup.productinquiryreply.dummy;

import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;

/**
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryReplyDummy {

    public static ProductInquiryReply getProductInquiryReply() {

        return ProductInquiryReply
            .builder()
            .productInquiryReplyTitle("답변제목")
            .productInquiryReplyContent("답변내용")
            .build();
    }
}
