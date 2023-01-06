package shop.itbook.itbookshop.orderproduct.entity;

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
import org.hibernate.annotations.ColumnDefault;
import shop.itbook.itbookshop.order.entity.Order;
import shop.itbook.itbookshop.product.entity.Product;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "count", nullable = false, columnDefinition = "integer default 1")
    private Integer count;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;


    /**
     * 주문상품 엔티티의 생성자입니다.
     *
     * @param order    the order
     * @param product  the product
     * @param count    the count
     * @param isHidden the is hidden
     * @author 노수연
     */
    @Builder
    public OrderProduct(Order order, Product product, Integer count, boolean isHidden) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.isHidden = isHidden;
    }
}
