package shop.itbook.itbookshop.ordergroup.ordernonmember.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Builder;
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

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "order_no")
    private Order order;

    @Column(name = "recipient_name", columnDefinition = "varchar(20)", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number", columnDefinition = "varchar(14)", nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "postcode", nullable = false)
    private Integer postcode;

    @Column(name = "road_name_address", columnDefinition = "varchar(255)", nullable = false)
    private String roadNameAddress;

    @Column(name = "recipient_address_details", columnDefinition = "varchar(255)", nullable = false)
    private String recipientAddressDetails;

    @Column(name = "non_member_order_code", nullable = false)
    private Long nonMemberOrderCode;

    /**
     * 주문 번호를 식별자키로 설정하는 생성자입니다.
     *
     * @param order                   주문 엔티티
     * @param recipientName           수령인 이름
     * @param recipientPhoneNumber    수령인 핸드폰번호
     * @param postcode                우편번호
     * @param roadNameAddress         도로명주소
     * @param recipientAddressDetails 상세주소
     * @param nonMemberOrderCode      주문확인을 위한 코드
     */
    @Builder
    public OrderNonMember(Order order, String recipientName, String recipientPhoneNumber,
                          Integer postcode, String roadNameAddress, String recipientAddressDetails,
                          Long nonMemberOrderCode) {
        this.orderNo = order.getOrderNo();
        this.order = order;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.postcode = postcode;
        this.roadNameAddress = roadNameAddress;
        this.recipientAddressDetails = recipientAddressDetails;
        this.nonMemberOrderCode = nonMemberOrderCode;
    }


}
