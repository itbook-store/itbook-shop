package shop.itbook.itbookshop.pointgroup.saving.savingpointhistory.order.entity;

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
import shop.itbook.itbookshop.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.saving.savingpointhistory.entity.SavingPointHistory;

/**
 * 주문으로 인해 포인트 적립이 된 정보를 저장하는 테이블과 매핑하는 엔티티 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_saving_point_history")
public class OrderSavingPointHistory {

    @Id
    private Long pointHistoryNo;
    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no")
    private SavingPointHistory savingPointHistory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false, unique = true)
    private Order order;
}
