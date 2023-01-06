package shop.itbook.itbookshop.pointgroup.usage.pointusageplace.entity;

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
import shop.itbook.itbookshop.pointgroup.usage.pointusageplace.pointusageplaceenum.PointUsagePlaceEnum;

/**
 * 포인트 사용처에 대한 상태값을 관리하는 테이블과 매핑하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_usage_place")
public class PointUsagePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_usage_place_no")
    private Integer pointUsagePlaceNo;

    @Column(name = "point_usage_place_name", nullable = false, unique = true, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private PointUsagePlaceEnum pointUsagePlaceEnum;
}
