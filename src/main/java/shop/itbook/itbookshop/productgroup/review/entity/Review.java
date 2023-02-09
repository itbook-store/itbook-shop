package shop.itbook.itbookshop.productgroup.review.entity;

import javax.persistence.CascadeType;
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
import lombok.ToString;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 리뷰에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    private Long orderProductNo;

    @MapsId("orderProductNo")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", nullable = false)
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "star_point", nullable = false)
    private Integer starPoint;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image")
    private String image;

    /**
     * 리뷰 엔티티 생성자입니다.
     *
     * @param orderProduct 주문한 상품 번호
     * @param product      주문한 상품
     * @param member       주문한 회원
     * @param starPoint    별점
     * @param content      리뷰 내용
     * @param image        사진 url
     * @author 노수연
     */
    @Builder
    public Review(OrderProduct orderProduct, Product product, Member member, Integer starPoint,
                  String content, String image) {
        this.orderProduct = orderProduct;
        this.product = product;
        this.member = member;
        this.starPoint = starPoint;
        this.content = content;
        this.image = image;
    }
}
