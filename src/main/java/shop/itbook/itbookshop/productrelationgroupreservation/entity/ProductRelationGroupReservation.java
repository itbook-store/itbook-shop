package shop.itbook.itbookshop.productrelationgroupreservation.entity;

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
import shop.itbook.itbookshop.product.entity.Product;
import shop.itbook.itbookshop.productrelationgroup.entity.ProductRelationGroup;

/**
 * 상품과 연관 상품 그룹 관계 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_relation_group_reservation")
public class ProductRelationGroupReservation {

    @EmbeddedId
    private Pk pk;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @MapsId("productRelationGroupNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_relation_group_reservation_no")
    private ProductRelationGroup productRelationGroup;

    /**
     * The type Pk. 관계 테이블의 복합키를 주키로 하기 위한 클래스 입니다.
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "product_no")
        private Integer productNo;

        @Column(name = "product_relation_group_no")
        private Long productRelationGroupNo;

    }

}
