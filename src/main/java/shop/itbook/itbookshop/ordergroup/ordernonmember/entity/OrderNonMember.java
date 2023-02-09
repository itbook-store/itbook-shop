package shop.itbook.itbookshop.ordergroup.ordernonmember.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 비회원의 주문을 담당하는 엔티티입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Setter
@Getter
@Table(name = "order_paper_non_member")
@Entity
public class OrderNonMember {
    @Id
    @Column(name = "order_no")
    private Long orderNo;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @PrimaryKeyJoinColumn(name = "order_no")
    private Order order;
    
    @Column(name = "non_member_order_code", nullable = false)
    private Long nonMemberOrderCode;

    /**
     * 주문 번호를 식별자키로 설정하는 생성자입니다.
     *
     * @param order              주문 엔티티
     * @param nonMemberOrderCode 주문확인을 위한 코드
     */
    public OrderNonMember(Order order, Long nonMemberOrderCode) {
        this.orderNo = order.getOrderNo();
        this.order = order;
        this.nonMemberOrderCode = nonMemberOrderCode;
    }
}
