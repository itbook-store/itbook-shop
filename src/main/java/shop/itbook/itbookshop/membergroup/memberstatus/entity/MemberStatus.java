package shop.itbook.itbookshop.membergroup.memberstatus.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnumConverter;

/**
 * Member Status에 관한 코드 테이블입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "member_status")
public class MemberStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_status_no")
    private Integer memberStatusNo;

    @Convert(converter = MemberStatusEnumConverter.class)
    @Column(name = "member_status_name", nullable = false,
        columnDefinition = "varchar(255)", unique = true)
    private MemberStatusEnum memberStatusEnum;

    @Builder
    public MemberStatus(Integer memberStatusNo, MemberStatusEnum memberStatusEnum) {
        this.memberStatusNo = memberStatusNo;
        this.memberStatusEnum = memberStatusEnum;
    }
}
