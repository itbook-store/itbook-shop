package shop.itbook.itbookshop.ordergroup.order.entity;

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
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * 주문에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_paper")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_destination_no", nullable = false)
    private MemberDestination memberDestination;

    @Column(name = "order_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime orderCreatedAt;

    @Column(name = "is_subscribed", nullable = false)
    private boolean isSubscribed;

    @Column(name = "selected_delivery_date", nullable = false, columnDefinition = "default now()")
    private LocalDateTime countSpecifiedDeliveryDate;

    /**
     * 주문 엔티티의 생성자입니다.
     *
     * @param member            the member
     * @param memberDestination the member destination
     * @param isSubscribed      the is subscribed
     * @author 노수연
     */
    @Builder
    public Order(Member member, MemberDestination memberDestination, boolean isSubscribed) {
        this.member = member;
        this.memberDestination = memberDestination;
        this.isSubscribed = isSubscribed;

        this.orderCreatedAt = LocalDateTime.now();
        this.countSpecifiedDeliveryDate = LocalDateTime.now();
    }
}
