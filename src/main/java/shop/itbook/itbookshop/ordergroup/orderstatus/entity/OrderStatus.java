package shop.itbook.itbookshop.ordergroup.orderstatus.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.converter.OrderStatusEnumConverter;

/**
 * 주문상태에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_status")
@Entity
public class OrderStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_no", nullable = false)
    private Integer orderStatusNo;

    @Convert(converter = OrderStatusEnumConverter.class)
    @Column(name = "order_status_name", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private OrderStatusEnum orderStatusEnum;
}

