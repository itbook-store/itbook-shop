package shop.itbook.itbookshop.ordergroup.orderproducthistory.entity;

import java.time.LocalDateTime;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;

/**
 * 주문 상품 이력에 대한 엔티티입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product_history")
@Entity
public class OrderProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_order_status_no")
    private Long orderProductOrderStatusNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", nullable = false)
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_no", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_status_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime orderStatusCreatedAt;

    @Builder
    public OrderProductHistory(OrderProduct orderProduct, OrderStatus orderStatus) {
        this.orderProduct = orderProduct;
        this.orderStatus = orderStatus;
    }
}
