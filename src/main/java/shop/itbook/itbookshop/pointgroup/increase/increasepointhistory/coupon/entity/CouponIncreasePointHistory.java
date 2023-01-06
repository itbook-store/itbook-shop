package shop.itbook.itbookshop.pointgroup.increase.increasepointhistory.coupon.entity;

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
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.pointgroup.increase.increasepointhistory.entity.IncreasePointHistory;

/**
 * 쿠폰 적립에 대한 정보를 저장하는 테이블과 매핑되는 엔티티 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon_increase_point_history")
public class CouponIncreasePointHistory {

    @Id
    private Long pointHistoryNo;

    @MapsId("pointHistoryNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_history_no", nullable = false)
    private IncreasePointHistory increasePointHistory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no", nullable = false, unique = true)
    private Coupon coupon;
}
