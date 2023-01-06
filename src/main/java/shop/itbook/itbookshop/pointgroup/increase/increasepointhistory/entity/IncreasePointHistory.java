package shop.itbook.itbookshop.pointgroup.increase.increasepointhistory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.pointgroup.common.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.increase.increasepointplace.entity.IncreasePointPlace;

/**
 * 포인트내역중에서도 적립된 포인트에 대한 테이블과 매핑되는 entity 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "increase_point_history")
public class IncreasePointHistory {

    @Id
    private Long pointHistoryNo;

    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no", nullable = false)
    private PointHistory pointHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_increase_place_no", nullable = false)
    private IncreasePointPlace increasePointPlace;

    @Column(name = "increase_point", nullable = false)
    private Long increasePoint;
}
