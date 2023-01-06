package shop.itbook.itbookshop.membergroup.membershiphistory.entity;

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
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * 회원등급에 대한 이력 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membership_history")
public class MembershipHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_history_no")
    private Long membershipHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_no", nullable = false)
    private Membership membership;

    @Column(name = "monthly_usage_amount", nullable = false)
    private Long monthlyUsageAmount;

    @Column(name = "membership_history_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime membershipHistoryCreatedAt;

    /**
     * 회원 등급 이력 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param member             the member
     * @param membership         the membership
     * @param monthlyUsageAmount the monthly usage amount
     * @author 강명관
     */
    @Builder
    public MembershipHistory(Member member, Membership membership, Long monthlyUsageAmount) {
        this.member = member;
        this.membership = membership;
        this.monthlyUsageAmount = monthlyUsageAmount;
    }
}
