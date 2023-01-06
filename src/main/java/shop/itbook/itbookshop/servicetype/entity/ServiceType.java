package shop.itbook.itbookshop.servicetype.entity;

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
import shop.itbook.itbookshop.servicetype.servicetypeenum.ServiceTypeEnum;

/**
 * 서비스유형에 관한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_type")
@Entity
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_no", nullable = false)
    private Integer serviceTypeNo;

    @Column(name = "service_type_name", nullable = false, columnDefinition = "varchar(255)", unique = true)
    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum serviceTypeName;
}
