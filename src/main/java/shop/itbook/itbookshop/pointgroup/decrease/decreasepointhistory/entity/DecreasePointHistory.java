package shop.itbook.itbookshop.pointgroup.decrease.decreasepointhistory.entity;

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
import shop.itbook.itbookshop.pointgroup.decrease.decreasepointplace.entity.DecreasePointPlace;

/**
 * 포인트 내역중에서도 차감에 대한 포인트내역을 나타내는 테이블과 매핑하는 엔티티클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "decrease_point_history")
public class DecreasePointHistory {

    @Id
    private Long pointHistoryNo;

    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no", nullable = false)
    private PointHistory pointHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decrease_point_place_no", nullable = false)
    private DecreasePointPlace decreasePointPlace;

    @Column(name = "decrease_point", nullable = false)
    private Long decreasePoint;
}
