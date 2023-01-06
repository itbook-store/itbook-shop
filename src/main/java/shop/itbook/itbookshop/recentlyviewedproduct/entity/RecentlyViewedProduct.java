package shop.itbook.itbookshop.recentlyviewedproduct.entity;

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
import shop.itbook.itbookshop.product.entity.Product;

/**
 * 최근 본 상품 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recently_viewed_product")
public class RecentlyViewedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recently_viewed_product_no")
    private Long recentlyViewedProductNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;
    
}
