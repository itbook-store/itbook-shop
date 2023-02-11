package shop.itbook.itbookshop.ordergroup.orderstatushistory.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;

/**
 * 주문의 상태 변경 이력을 저장하는 엔티티입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_status_history")
@Entity
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_history_no")
    private Long orderStatusHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_no", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_status_created_at", nullable = false)
    private LocalDateTime orderStatusCreatedAt;

    /**
     * 주문 엔티티 인스턴스와 주문 상태 엔티티 인스턴스를 받아 생성하느 생성자입니다.
     *
     * @param order                주문 엔티티
     * @param orderStatus          주문 상태 엔티티의 인스턴스
     * @param orderStatusCreatedAt 이력 추가 날짜
     */
    public OrderStatusHistory(Order order, OrderStatus orderStatus,
                              LocalDateTime orderStatusCreatedAt) {
        this.order = order;
        this.orderStatus = orderStatus;
        this.orderStatusCreatedAt = orderStatusCreatedAt;
    }
}
