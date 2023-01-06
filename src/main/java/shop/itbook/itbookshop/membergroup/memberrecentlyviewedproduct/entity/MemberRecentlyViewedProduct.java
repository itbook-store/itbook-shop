package shop.itbook.itbookshop.membergroup.memberrecentlyviewedproduct.entity;

import java.io.Serializable;
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
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.recentlyviewedproduct.entity.RecentlyViewedProduct;

/**
 * 회원 최근 본 상품 관계 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_recently_viewed_product")
public class MemberRecentlyViewedProduct {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @MapsId("recentlyViewedProductNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recently_viewed_product_no", nullable = false)
    private RecentlyViewedProduct recentlyViewedProduct;

    /**
     * The type Pk. 회원과 최근본 상품 복합키를 주키로 하기위한 클래스 입니다.
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        private Long memberNo;

        private Long recentlyViewedProductNo;

    }
}
