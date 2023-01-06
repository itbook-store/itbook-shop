package shop.itbook.itbookshop.couponissue.entity;

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
import shop.itbook.itbookshop.coupon.entity.Coupon;
import shop.itbook.itbookshop.member.entity.Member;
import shop.itbook.itbookshop.usagestatus.entity.UsageStatus;

/**
 * 쿠폰_발급 이력을 관리하는 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon_issue")
@Entity
public class CouponIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_issue_no", nullable = false)
    private Long couponIssueNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_status_no")
    private UsageStatus usageStatus;

    @Column(name = "coupon_issue_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime couponIssueCreatedAt;

    @Column(name = "coupon_usage_created_at", nullable = false)
    private LocalDateTime couponUsageCreatedAt;

    /**
     * 쿠폰 발급 테이블의 생성자 입니다.
     *
     * @param member               the member
     * @param coupon               the coupon
     * @param usageStatus          the usage status
     * @author 송다혜
     */
    @Builder
    public CouponIssue(Member member, Coupon coupon, UsageStatus usageStatus) {
        this.member = member;
        this.coupon = coupon;
        this.usageStatus = usageStatus;
    }
}
