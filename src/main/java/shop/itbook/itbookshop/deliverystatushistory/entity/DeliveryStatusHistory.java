package shop.itbook.itbookshop.deliverystatushistory.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverystatus.entity.DeliveryStatus;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_status_history", uniqueConstraints = {
    @UniqueConstraint(name = "UniqueDeliveryAndDeliveryStatus", columnNames = {"delivery_no",
        "delivery_status_no"})})
public class DeliveryStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_status_history_no")
    private Long deliveryStatusHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_no", nullable = false)
    private Delivery delivery;

    @Column(name = "delivery_status_created_at", nullable = false)
    private LocalDateTime deliveryStatusCreatedAt;

    @Column(name = "history_location", nullable = false, columnDefinition = "varchar(100)")
    private String historyLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_status_no", nullable = false)
    private DeliveryStatus deliveryStatus;
}
