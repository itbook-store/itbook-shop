package shop.itbook.itbookshop.productgroup.producttyperegistration.entity;

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
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 상품 유형 등록 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_type_registration")
public class ProductTypeRegistration {

    @EmbeddedId
    private Pk pk;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @MapsId("productTypeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_no", nullable = false)
    private ProductType productType;

    public ProductTypeRegistration(Product product, ProductType productType) {
        this.product = product;
        this.productType = productType;
        this.setPk(new Pk(product.getProductNo(), productType.getProductTypeNo()));
    }

    /**
     * The type Pk. 상품과 상품타입 복합키를 주키로 하기위한 클래스 입니다.
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
        private Long productNo;
        private Integer productTypeNo;
    }
}
