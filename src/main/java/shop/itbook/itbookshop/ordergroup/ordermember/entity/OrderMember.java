package shop.itbook.itbookshop.ordergroup.ordermember.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
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

    @Id
    @Column(name = "order_no")
    private Long orderNo;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @PrimaryKeyJoinColumn(name = "order_no")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    /**
     * 회원 주문 엔티티의 생성자입니다.
     *
     * @param order  주문 엔티티
     * @param member 회원 엔티티
     */
    public OrderMember(Order order, Member member) {
        this.orderNo = order.getOrderNo();
        this.order = order;
        this.member = member;
    }

}
