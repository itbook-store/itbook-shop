package shop.itbook.itbookshop.productgroup.producttag.entity;

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
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.tag.entity.Tag;

/**
 * 상품과 태그 관계 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_tag")
public class ProductTag {

    @EmbeddedId
    private Pk pk;

    @MapsId("tagNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no", nullable = false)
    private Tag tag;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;


    /**
     * The type Pk. 관계 테이블의 복합키를 주키로 하기 위한 클래스 입니다.
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

        private Integer tagNo;

        private Long productNo;

    }
}

