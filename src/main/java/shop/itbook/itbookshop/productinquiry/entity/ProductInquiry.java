package shop.itbook.itbookshop.productinquiry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.product.entity.Product;

/**
 * 상품 문의에 대한 엔티티 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_inquiry")
@Entity
public class ProductInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inquiry_no")
    private Long productInquiryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(20)")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(name = "is_replied", nullable = false)
    private boolean isReplied;

    /**
     * 상품 문의 엔티티의 생성자입니다.
     *
     * @param member    the member
     * @param product   the product
     * @param title     the title
     * @param content   the content
     * @param isPublic  the is public
     * @param isReplied the is replied
     * @author 정재원
     */
    @Builder
    public ProductInquiry(Member member, Product product, String title, String content,
                          boolean isPublic, boolean isReplied) {
        this.member = member;
        this.product = product;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.isReplied = isReplied;
    }
}
