package shop.itbook.itbookshop.productgroup.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * 리뷰에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    private Long orderProductNo;

    @MapsId("orderProductNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", nullable = false)
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "start_point", nullable = false)
    private Integer startPoint;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "image", columnDefinition = "text")
    private String image;

    /**
     * 리뷰 엔티티 생성자입니다.
     *
     * @param orderProduct the order product
     * @param product      the product
     * @param member       the member
     * @param startPoint   the star point
     * @param content      the review content
     * @param image        the image
     * @author 노수연
     */
    @Builder
    public Review(OrderProduct orderProduct, Product product, Member member, Integer startPoint,
                  String content, String image) {
        this.orderProduct = orderProduct;
        this.product = product;
        this.member = member;
        this.startPoint = startPoint;
        this.content = content;
        this.image = image;
    }
}
