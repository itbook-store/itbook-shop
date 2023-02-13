package shop.itbook.itbookshop.productgroup.productinquiryreply.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;

/**
 * 상품 문의 댓글 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_inquiry_reply")
public class ProductInquiryReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inquiry_reply_no")
    private Integer productInquiryReplyNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_inquiry_no", nullable = false)
    private ProductInquiry productInquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "product_inquiry_reply_title", nullable = false)
    private String productInquiryReplyTitle;

    @Column(name = "product_inquiry_reply_content", nullable = false)
    private String productInquiryReplyContent;


    /**
     * 상품 문의 댓글 엔티티에 대한 생성자 입니다.
     *
     * @param productInquiry             the product inquiry
     * @param member                     the member
     * @param productInquiryReplyTitle   the product inquiry reply title
     * @param productInquiryReplyContent the product inquiry reply content
     * @author 강명관
     */
    @Builder
    public ProductInquiryReply(ProductInquiry productInquiry, Member member,
                               String productInquiryReplyTitle, String productInquiryReplyContent) {
        this.productInquiry = productInquiry;
        this.member = member;
        this.productInquiryReplyTitle = productInquiryReplyTitle;
        this.productInquiryReplyContent = productInquiryReplyContent;
    }
}
