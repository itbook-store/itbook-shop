package shop.itbook.itbookshop.ordergroup.ordernonmember.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
    @EmbeddedId
    private Pk pk;

    @MapsId("orderNo")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_no", nullable = false)
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
     * 주문 번호로 PK 를 설정하는 생성자입니다.
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
        this.pk = new Pk(order.getOrderNo());
        this.order = order;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.postcode = postcode;
        this.roadNameAddress = roadNameAddress;
        this.recipientAddressDetails = recipientAddressDetails;
        this.nonMemberOrderCode = nonMemberOrderCode;
    }


}
