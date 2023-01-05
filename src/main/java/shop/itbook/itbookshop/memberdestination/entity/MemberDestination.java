package shop.itbook.itbookshop.memberdestination.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.deliverydestination.entity.DeliveryDestination;
import shop.itbook.itbookshop.member.entity.Member;

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
    @JoinColumn(name = "delivery_destination_no")
    private DeliveryDestination deliveryDestinationNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @Column(name = "recipient_name", columnDefinition = "varchar(20)")
    private String recipientName;

    @Column(name = "recipient_phone_number", columnDefinition = "varchar(14)")
    private String recipientPhoneNumber;

    @Column(name = "recipient_address_details", columnDefinition = "varchar(255)")
    private String recipientAddressDetails;

}
