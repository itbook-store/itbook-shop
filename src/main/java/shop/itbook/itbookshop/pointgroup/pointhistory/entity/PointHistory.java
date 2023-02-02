package shop.itbook.itbookshop.pointgroup.pointhistory.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;

/**
 * 포인트 내역 테이블과 매핑되는 entity 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_no")
    private Long pointHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_increase_decrease_content_no", nullable = false)
    private PointIncreaseDecreaseContent pointIncreaseDecreaseContent;

    @Column(name = "increase_decrease_point", nullable = false)
    private Integer increaseDecreasePoint;

    @Column(name = "remained_point", nullable = false)
    private Integer remainedPoint;

    @Column(name = "history_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime historyCreatedAt;

    /**
     * entity 객체를 생성하기 위한 Builder 전용 생성자입니다.
     *
     * @param member           포인트 적립 및 차감의 주체가되는 회원에 대한 정보를 담은 entity 객체입니다.
     * @param remainedPoint    회원의 잔여포인트를 의미합니다.
     * @param historyCreatedAt 포인트 내역이 생성된 일자를 의미합니다.
     */
    @Builder
    public PointHistory(Member member, PointIncreaseDecreaseContent pointIncreaseDecreaseContent,
                        Integer increaseDecreasePoint, Integer remainedPoint) {
        this.member = member;
        this.pointIncreaseDecreaseContent = pointIncreaseDecreaseContent;
        this.increaseDecreasePoint = increaseDecreasePoint;
        this.remainedPoint = remainedPoint;
        this.historyCreatedAt = LocalDateTime.now();
    }
}
