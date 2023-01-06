package shop.itbook.itbookshop.pointgroup.decrease.decreasepointplace.entity;

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
import shop.itbook.itbookshop.pointgroup.decrease.decreasepointplace.pointusageplaceenum.DecreasePointPlaceEnum;

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
@Table(name = "point_decrease_place")
public class DecreasePointPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "decrease_point_place_no")
    private Integer decreasePointPlaceNo;

    @Column(name = "decrease_point_place_name", nullable = false, unique = true,
        columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private DecreasePointPlaceEnum decreasePointPlaceEnum;
}
