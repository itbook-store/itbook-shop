package shop.itbook.itbookshop.ordergroup.order.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;


    public Order(Long orderNo, LocalDateTime orderCreatedAt, LocalDateTime selectedDeliveryDate) {
        this.orderNo = orderNo;
        this.orderCreatedAt = orderCreatedAt;
        this.selectedDeliveryDate = selectedDeliveryDate;
    }
}
