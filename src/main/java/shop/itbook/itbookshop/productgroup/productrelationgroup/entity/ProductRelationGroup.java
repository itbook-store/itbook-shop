package shop.itbook.itbookshop.productgroup.productrelationgroup.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 연관 상품 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_relation_group")
public class ProductRelationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_relation_group_no")
    private Long productRelationGroupNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "based_product_no", nullable = false)
    private Product basedProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public ProductRelationGroup(Product basedProduct, Product product) {
        this.basedProduct = basedProduct;
        this.product = product;
        this.isDeleted = false;
    }
}
