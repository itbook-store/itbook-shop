package shop.itbook.itbookshop.membergroup.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * 회원에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_no", nullable = false)
    private Membership membership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no", nullable = false)
    private MemberStatus memberStatus;

    @Column(name = "id", nullable = false, columnDefinition = "varchar(15)", unique = true)
    private String id;

    @Column(name = "nickname", nullable = false, columnDefinition = "varchar(20)", unique = true)
    private String nickname;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @Column(name = "is_man", nullable = false)
    private boolean isMan;

    @Column(name = "birth", nullable = false)
    private LocalDateTime birth;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(14)", unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private String email;

    @Column(name = "member_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime memberCreatedAt;

    /**
     * 회원 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param membership   the membership
     * @param memberStatus the member status
     * @param id           the id
     * @param nickname     the nickname
     * @param name         the name
     * @param isGender     the is gender
     * @param birth        the birth
     * @param password     the password
     * @param phoneNumber  the phone number
     * @param email        the email
     * @author 강명관
     */
    @SuppressWarnings("java:S107") // 회원 테이블의 입력 받아야 될 필드값이 많기 때문
    @Builder
    public Member(Membership membership, MemberStatus memberStatus, String id, String nickname,
                  String name, boolean isMan, LocalDateTime birth, String password,
                  String phoneNumber, String email) {
        this.membership = membership;
        this.memberStatus = memberStatus;
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.isMan = isMan;
        this.birth = birth;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
