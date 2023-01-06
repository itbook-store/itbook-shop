package shop.itbook.itbookshop.deliverygroup.deliverydestination.entity;

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
 * 배송지에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_destination")
@Entity
public class DeliveryDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_destination_no")
    private Integer deliveryDestinationNo;

    @Column(name = "postcode", nullable = false)
    private Integer postcode;

    @Column(name = "address", nullable = false, unique = true, columnDefinition = "varchar(255)")
    private String address;
}
