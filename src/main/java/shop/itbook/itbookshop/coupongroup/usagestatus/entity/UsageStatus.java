package shop.itbook.itbookshop.coupongroup.usagestatus.entity;

import javax.persistence.Column;
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
import shop.itbook.itbookshop.coupongroup.usagestatusenum.UsageStatusEnum;

/**
 * 쿠폰 적용 범위 에 대한 엔터티 입니다.
 *
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
    @Column(name = "usage_status_no", nullable = false)
    private Integer usageStatusNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "usage_status_name", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private UsageStatusEnum usageStatusEnum;
}
