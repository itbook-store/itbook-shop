package shop.itbook.itbookshop.ordersubscriptionhistory.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordersubscription.entity.OrderSubscription;

/**
 * 구독주문이력에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_subscription_history")
public class OrderSubscriptionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_product_order_status_no")
    private Long subscriptionProductOrderStatusNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_no", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_status_created_at", nullable = false)
    private LocalDateTime orderStatusCreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_subscription_no", nullable = false)
    private OrderSubscription orderSubscription;
}