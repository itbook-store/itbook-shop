package shop.itbook.itbookshop.membergroup.memberdestination.entity;

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
import shop.itbook.itbookshop.deliverygroup.deliverydestination.entity.DeliveryDestination;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 회원 배송지에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_destination")
public class MemberDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_destination_no")
    private Long recipientDestinationNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_destination_no", nullable = false)
    private DeliveryDestination deliveryDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "recipient_name", columnDefinition = "varchar(20)", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number", columnDefinition = "varchar(14)", nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "recipient_address_details", columnDefinition = "varchar(255)", nullable = false)
    private String recipientAddressDetails;

    /**
     * 회원 배송지 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param deliveryDestination     the delivery destination
     * @param member                  the member
     * @param recipientName           the recipient name
     * @param recipientPhoneNumber    the recipient phone number
     * @param recipientAddressDetails the recipient address details
     * @author 강명관
     */
    @Builder
    public MemberDestination(DeliveryDestination deliveryDestination, Member member,
                             String recipientName, String recipientPhoneNumber,
                             String recipientAddressDetails) {
        this.deliveryDestination = deliveryDestination;
        this.member = member;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.recipientAddressDetails = recipientAddressDetails;
    }
}
