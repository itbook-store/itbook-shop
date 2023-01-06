package shop.itbook.itbookshop.pointgroup.decrease.decreasepointhistory.gift.entity;

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
import shop.itbook.itbookshop.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.decrease.decreasepointhistory.entity.DecreasePointHistory;

/**
 * 선물을 해줌으로 인해서 포인트가 사용된 기록을 관리하는 테이블과 매핑되는 엔티티클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gift_used_point_history")
public class GifDecreasePointHistory {

    @Id
    private Long pointHistoryNo;
    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no")
    private DecreasePointHistory decreasePointHistory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

}
