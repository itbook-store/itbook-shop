package shop.itbook.itbookshop.subscriptionorderstatus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 구독주문 상태에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription_order_status")
@Entity
public class SubscriptionOrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_no", nullable = false)
    private Integer orderStatusNo;

    @Column(name = "order_status_name", nullable = false, columnDefinition = "varchar(20)")
    private String orderStatusName;
}
