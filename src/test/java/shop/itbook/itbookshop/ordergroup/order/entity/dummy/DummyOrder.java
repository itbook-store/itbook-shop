package shop.itbook.itbookshop.ordergroup.order.entity.dummy;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 테스트를 위한 Order 더미 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@Table(name = "order")
@Entity
public class DummyOrder extends Order {

    @Id
    @Column(name = "order_no")
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_destination_no")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private MemberDestination memberDestination;

    @Column(name = "order_created_at", columnDefinition = "default now()")
    private LocalDateTime orderCreatedAt;

    @Transient
    @Column(name = "is_subscribed")
    private boolean isSubscribed;

    @Transient
    @Column(name = "selected_delivery_date", columnDefinition = "default now()")
    private LocalDateTime countSpecifiedDeliveryDate;

    public DummyOrder(Long orderNo, Member member, MemberDestination memberDestination,
                      LocalDateTime orderCreatedAt, boolean isSubscribed,
                      LocalDateTime countSpecifiedDeliveryDate) {
        this.orderNo = orderNo;
        this.member = member;
        this.memberDestination = memberDestination;
        this.orderCreatedAt = orderCreatedAt;
        this.isSubscribed = isSubscribed;
        this.countSpecifiedDeliveryDate = countSpecifiedDeliveryDate;
    }

    public DummyOrder() {

    }
}
