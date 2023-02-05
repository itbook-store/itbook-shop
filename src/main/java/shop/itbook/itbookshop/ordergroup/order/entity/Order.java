package shop.itbook.itbookshop.ordergroup.order.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * 주문에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Table(name = "order_paper")
@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "order_created_at", nullable = false)
    private LocalDateTime orderCreatedAt;

    @Column(name = "selected_delivery_date", nullable = false)
    private LocalDateTime selectedDeliveryDate;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

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

    /**
     * 주문 엔티티의 생성자입니다.
     *
     * @param orderCreatedAt          주문 생성일
     * @param selectedDeliveryDate    지정 배송일
     * @param recipientName           수령인 이름
     * @param recipientPhoneNumber    수령인 핸드폰번호
     * @param postcode                우편번호
     * @param roadNameAddress         도로명주소
     * @param recipientAddressDetails 상세주소
     * @author 정재원
     */
    @Builder
    public Order(LocalDateTime orderCreatedAt, LocalDateTime selectedDeliveryDate,
                 String recipientName,
                 String recipientPhoneNumber, Integer postcode, String roadNameAddress,
                 String recipientAddressDetails) {
        this.orderCreatedAt = orderCreatedAt;
        this.selectedDeliveryDate = selectedDeliveryDate;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.postcode = postcode;
        this.roadNameAddress = roadNameAddress;
        this.recipientAddressDetails = recipientAddressDetails;
    }
}
