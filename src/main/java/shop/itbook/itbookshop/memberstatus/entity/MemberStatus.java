package shop.itbook.itbookshop.memberstatus.entity;

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
import lombok.extern.java.Log;

/**
 * Member Status에 관한 코드 테이블입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_status")
public class MemberStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_status_no")
    private Integer memberStatusNo;

    @Column(name = "member_status_name", nullable = false, columnDefinition = "varchar(20)")
    private String memberStatusName;
}
