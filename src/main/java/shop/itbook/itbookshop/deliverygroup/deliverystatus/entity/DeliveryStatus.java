package shop.itbook.itbookshop.deliverygroup.deliverystatus.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.converter.DeliveryStatusEnumConverter;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;

/**
 * 배송상태에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_status")
@Entity
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_status_no", nullable = false)
    private Integer deliveryStatusNo;

    @Convert(converter = DeliveryStatusEnumConverter.class)
    @Column(name = "delivery_status_name", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private DeliveryStatusEnum deliveryStatusEnum;
}
