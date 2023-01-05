package shop.itbook.itbookshop.productcategory.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.categori.entity.Category;
import shop.itbook.itbookshop.product.entity.Product;

/**
 * 상품과 카테고리의 관계테이블입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class ProductCategory {

    @EmbeddedId
    private Pk pk;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @MapsId("categoryNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Pk implements Serializable {
        @Id
        private Long productNo;

        @Id
        private Integer categoryNo;
    }
}
