package shop.itbook.itbookshop.deliverygroup.delivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 배송에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
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

    @Column(name = "tracking_no", columnDefinition = "varchar(255)",
        unique = true, nullable = false)
    private String trackingNo;

    /**
     * 배송 번호를 제외하고 배송 엔티티의 객체를 생성하는 생성자입니다.
     *
     * @param order      배송할 주문의 정보
     * @param trackingNo 운송장 번호
     */
    public Delivery(Order order, String trackingNo) {
        this.order = order;
        this.trackingNo = trackingNo;
    }
}
