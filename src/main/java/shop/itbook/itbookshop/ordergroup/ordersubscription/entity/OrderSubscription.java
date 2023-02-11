package shop.itbook.itbookshop.ordergroup.ordersubscription.entity;

import java.time.LocalDate;
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
import lombok.Builder;
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @PrimaryKeyJoinColumn(name = "order_no")
    private Order order;

    @Column(name = "subscription_start_date", nullable = false)
    private LocalDate subscriptionStartDate;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "subscription_period", nullable = false)
    private Integer subscriptionPeriod;

    // 추후 확장성 고려
    @Column(name = "subscription_delivery_day", nullable = false, columnDefinition = "integer default 1")
    private Integer subscriptionDeliveryDay;

    /**
     * 주문 구독 엔티티의 생성자 입니다.
     * 주문 엔티티의 Pk 를 식별자 값으로 가집니다.
     *
     * @param order                 주문 엔티티
     * @param subscriptionStartDate 구독 시작일
     * @param sequence              구독 발송 순서
     * @param subscriptionPeriod    구독 기간
     */
    @Builder
    public OrderSubscription(Order order, LocalDate subscriptionStartDate, Integer sequence,
                             Integer subscriptionPeriod) {
        this.order = order;
        this.subscriptionStartDate = subscriptionStartDate;
        this.sequence = sequence;
        this.subscriptionPeriod = subscriptionPeriod;
        this.subscriptionDeliveryDay = 1;
    }
}
