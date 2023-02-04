package shop.itbook.itbookshop.ordergroup.ordermember.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 회원의 주문을 담당하는 엔티티입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Setter
@Getter
@Table(name = "order_paper_member")
@Entity
public class OrderMember {
    @EmbeddedId
    private Pk pk;

    @MapsId("orderNo")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_destination_no", nullable = false)
    private MemberDestination memberDestination;

    /**
     * Order paper Pk 인 주문 번호를 식별키로 사용합니다.
     *
     * @author 정재원
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        private Long orderNo;
    }

    /**
     * 주문 번호로 Pk 를 설정하는 생성자입니다.
     *
     * @param order             주문 엔티티
     * @param memberDestination 회원 배송지 엔티티
     */
    public OrderMember(Order order, MemberDestination memberDestination) {
//        this.pk = new Pk(order.getOrderNo());
        this.order = order;
        this.memberDestination = memberDestination;
    }
}
