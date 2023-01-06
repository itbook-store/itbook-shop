package shop.itbook.itbookshop.role.entity;

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
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * 회원 권환에 관한 테이블 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_no")
    private Integer roleNo;

    @Column(name = "role_type", columnDefinition = "varchar(255)", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleType;
}
