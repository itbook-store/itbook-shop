package shop.itbook.itbookshop.ordergroup.order.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;

/**
 * 주문에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@ToString
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
    private LocalDate selectedDeliveryDate;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE})
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE})
    private List<OrderStatusHistory> orderStatusHistories;


    @OneToOne(mappedBy = "order", cascade = {CascadeType.REMOVE})
    private OrderMember orderMember;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.REMOVE})
    private OrderNonMember orderNonMember;


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

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Column(name = "increase_point", nullable = false, columnDefinition = "bigint default 0")
    private Long increasePoint;

    @Column(name = "decrease_point", nullable = false, columnDefinition = "bigint default 0")
    private Long decreasePoint;

    @Column(name = "delivery_fee", columnDefinition = "bigint default 0")
    private Long deliveryFee;

    @Column(name = "amount")
    private Long amount;

    /**
     * 주문 엔티티의 생성자입니다.
     *
     * @param orderCreatedAt          주문 생성일
     * @param selectedDeliveryDate    지정 배송일
     * @param orderProducts           주문 상품 리스트
     * @param recipientName           수령인 이름
     * @param recipientPhoneNumber    수령인 핸드폰번호
     * @param postcode                우편번호
     * @param roadNameAddress         도로명주소
     * @param recipientAddressDetails 상세주소
     * @param isHidden                주문 내역에서 숨김 여부
     * @param increasePoint           주문으로 적립된 포인트
     * @param decreasePoint           주문으로 사용된 포인트(구매시 사용됨)
     * @param deliveryFee             배송비
     * @param amount                  주문 금액
     * @author 정재원
     */
    @Builder
    public Order(LocalDateTime orderCreatedAt, LocalDate selectedDeliveryDate,
                 List<OrderProduct> orderProducts, String recipientName,
                 String recipientPhoneNumber,
                 Integer postcode, String roadNameAddress, String recipientAddressDetails,
                 Boolean isHidden, Long increasePoint, Long decreasePoint, Long deliveryFee,
                 Long amount) {
        this.orderCreatedAt = orderCreatedAt;
        this.selectedDeliveryDate = selectedDeliveryDate;
        this.orderProducts = orderProducts;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.postcode = postcode;
        this.roadNameAddress = roadNameAddress;
        this.recipientAddressDetails = recipientAddressDetails;
        this.isHidden = isHidden;
        this.increasePoint = increasePoint;
        this.decreasePoint = decreasePoint;
        this.deliveryFee = deliveryFee;
        this.amount = amount;
    }
}
