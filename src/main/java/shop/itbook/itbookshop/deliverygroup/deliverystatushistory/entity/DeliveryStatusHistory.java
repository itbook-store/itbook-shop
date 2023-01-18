package shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.DeliveryStatus;

/**
 * 배송상태이력에 대한 엔티티입니다.
 *
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

    @Column(name = "delivery_status_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime deliveryStatusCreatedAt;

    @Column(name = "history_location", nullable = false, columnDefinition = "varchar(100)")
    private String historyLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_status_no", nullable = false)
    private DeliveryStatus deliveryStatus;


    /**
     * 배송상태이력 엔티티의 생성자입니다.
     *
     * @param delivery        the delivery
     * @param historyLocation the history location
     * @param deliveryStatus  the delivery status
     * @author 노수연
     */
    @Builder
    public DeliveryStatusHistory(Delivery delivery,
                                 String historyLocation, DeliveryStatus deliveryStatus) {
        this.delivery = delivery;
        this.historyLocation = historyLocation;
        this.deliveryStatus = deliveryStatus;

        this.deliveryStatusCreatedAt = LocalDateTime.now();
    }
}
