package shop.itbook.itbookshop.ordergroup.order.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 주문에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Table(name = "order_paper")
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

    /**
     * 주문 엔티티의 생성자입니다.
     *
     * @author 정재원
     */
    public Order() {
        this.orderCreatedAt = LocalDateTime.now();
        this.selectedDeliveryDate = LocalDateTime.now();
    }
}
