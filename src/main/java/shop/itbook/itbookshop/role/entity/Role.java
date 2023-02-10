package shop.itbook.itbookshop.role.entity;

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
import lombok.ToString;
import shop.itbook.itbookshop.role.converter.RoleEnumConverter;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * 회원 권환에 관한 테이블 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_no")
    private Integer roleNo;


    @Convert(converter = RoleEnumConverter.class)
    @Column(name = "role_type", columnDefinition = "varchar(255)", unique = true, nullable = false)
    private RoleEnum roleType;

    public Role(RoleEnum roleType) {
        this.roleType = roleType;
    }
}
