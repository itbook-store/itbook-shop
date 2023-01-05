package shop.itbook.itbookshop.coupon.entity;

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
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usage_status")
@Entity
public class UsageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_status_no ", nullable = false)
    private Integer usageStatusNo;

    @Column(name = "usage_status_name", nullable = false, columnDefinition = "varchar(20)")
    private String usageStatusName;
}
