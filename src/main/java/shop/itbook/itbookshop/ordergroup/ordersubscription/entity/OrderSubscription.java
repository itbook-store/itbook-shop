package shop.itbook.itbookshop.ordergroup.ordersubscription.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 주문_구독에 대한 엔티티입니다.
 * 구독 상품을 주문할 시 구독을 위한 추가 정보를 저장합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_subscription")
public class OrderSubscription {

    @Id
    @Column
    private Long orderNo;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @PrimaryKeyJoinColumn(name = "order_no")
    private Order order;

    @Column(name = "subscription_period", nullable = false)
    private Integer subscriptionPeriod;

    @Column(name = "subscription_delivery_day", nullable = false)
    private Integer subscriptionDeliveryDay;

    /**
     * 주문 구독 엔티티의 생성자 입니다.
     * 주문 엔티티의 Pk 를 식별자 값으로 가집니다.
     *
     * @param order                   the order
     * @param subscriptionPeriod      the subscription period
     * @param subscriptionDeliveryDay the subscription delivery day
     */
    public OrderSubscription(Order order, Integer subscriptionPeriod,
                             Integer subscriptionDeliveryDay) {
        this.orderNo = order.getOrderNo();
        this.order = order;
        this.subscriptionPeriod = subscriptionPeriod;
        this.subscriptionDeliveryDay = subscriptionDeliveryDay;
    }
}
