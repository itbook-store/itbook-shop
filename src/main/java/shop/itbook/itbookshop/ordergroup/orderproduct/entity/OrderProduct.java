package shop.itbook.itbookshop.ordergroup.orderproduct.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문상품에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product",
    uniqueConstraints = {
        @UniqueConstraint(name = "UniqueOrderAndProduct", columnNames = {"order_no",
            "product_no"})})
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_no")
    private Long orderProductNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "count", nullable = false, columnDefinition = "integer default 1")
    private Integer count;

    @Column(name = "product_price", nullable = false)
    private Long productPrice;

    /**
     * 주문상품 엔티티의 생성자입니다.
     *
     * @param order        주문 엔티티
     * @param product      상품 엔티티
     * @param count        상품 개수
     * @param productPrice 구매할 당시 상품의 가격
     * @author 정재원
     */
    @Builder
    public OrderProduct(Order order, Product product, Integer count, Long productPrice) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.productPrice = productPrice;
    }
}
