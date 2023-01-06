package shop.itbook.itbookshop.pointgroup.decrease.decreasepointhistory.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.decrease.decreasepointhistory.entity.DecreasePointHistory;

/**
 * 주문할때 포인트가 차감된 경우의 데이타를 저장하는 테이블고의 연관관계를 나타내는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_decrease_point_history")
public class OrderDecreasePointHistory {

    @Id
    private Long pointHistoryNo;

    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no", nullable = false)
    private DecreasePointHistory decreasePointHistory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false, unique = true)
    private Order order;
}
