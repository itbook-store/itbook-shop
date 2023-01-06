package shop.itbook.itbookshop.membergroup.memberstatushistory.entity;

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
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * 회원 상태 이력에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_status_history")
public class MemberStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_status_history_no")
    private Long memberStatusHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no", nullable = false)
    private MemberStatus memberStatus;

    @Column(name = "member_status_history_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime memberStatusHistoryCreatedAt;

    @Column(name = "status_changed_reason", nullable = false, columnDefinition = "varchar(255)")
    private String statusChangedReason;


    /**
     * 회원 상태 이력 엔티티에 대한 생성자입니다.
     *
     * @param member              the member
     * @param memberStatus        the member status
     * @param statusChangedReason the status changed reason
     * @author 강명관
     */
    @Builder
    public MemberStatusHistory(Member member, MemberStatus memberStatus,
                               String statusChangedReason) {
        this.member = member;
        this.memberStatus = memberStatus;
        this.statusChangedReason = statusChangedReason;
    }
}
