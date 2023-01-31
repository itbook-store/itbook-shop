package shop.itbook.itbookshop.membergroup.memberrole.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * 회원 권한 관계 테이블에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_role")
public class MemberRole {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @MapsId("roleNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", nullable = false)
    private Role role;

    public MemberRole(Member member, Role role) {
        this.member = member;
        this.role = role;
        this.setPk(new MemberRole.Pk(member.getMemberNo(), role.getRoleNo()));
    }

    /**
     * The type Pk. 회원 권한테이블의 복합키를 주키로 하기위한 클래스입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        private Long memberNo;

        private Integer roleNo;

    }
}
