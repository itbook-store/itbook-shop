package shop.itbook.itbookshop.cart.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 장바구니 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @Column(name = "product_count", columnDefinition = "integer default 1")
    private Integer productCount;

    /**
     * The type Pk. 회원과 상품에 대한 복합키 설정을 위한 클래스 입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        private Long memberNo;

        private Long productNo;

    }
}

