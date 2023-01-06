package shop.itbook.itbookshop.delivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.order.entity.Order;

/**
 * 배송에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery")
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_no")
    private Long deliveryNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;

    @Column(name = "receiver_name", columnDefinition = "varchar(20)")
    private String receiverName;

    @Column(name = "receiver_address", columnDefinition = "varchar(255)")
    private String receiverAddress;

    @Column(name = "receiver_detail_address", columnDefinition = "varchar(255)")
    private String receiverDetailAddress;

    @Column(name = "receiver_phone_number", columnDefinition = "varchar(14)")
    private String receiverPhoneNumber;

    @Column(name = "tracking_no", columnDefinition = "varchar(255)", unique = true)
    private String trackingNo;

    /**
     * 배송 엔티티의 생성자 입니다.
     *
     * @param order                 the order
     * @param receiverName          the receiver name
     * @param receiverAddress       the receiver address
     * @param receiverDetailAddress the receiver detail address
     * @param receiverPhoneNumber   the receiver phone number
     * @param trackingNo            the tracking no
     * @author 노수연
     */
    @Builder
    public Delivery(Order order, String receiverName, String receiverAddress,
                    String receiverDetailAddress, String receiverPhoneNumber, String trackingNo) {
        this.order = order;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverDetailAddress = receiverDetailAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.trackingNo = trackingNo;
    }
}
