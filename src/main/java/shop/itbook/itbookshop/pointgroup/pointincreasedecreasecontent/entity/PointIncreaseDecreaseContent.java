package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity;

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
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * 포인트 적립처 상태를 저장하기 위한 테이블과 매핑되는 엔티티클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_increase_decrease_content")
public class PointIncreaseDecreaseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_increase_decrease_content_no")
    private Integer pointIncreaseDecreaseContentNo;

    @Column(name = "content", columnDefinition = "varchar(255)",
        nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PointIncreaseDecreaseContentEnum contentEnum;

    @Column(name = "is_decrease")
    private Boolean isDecrease;
}