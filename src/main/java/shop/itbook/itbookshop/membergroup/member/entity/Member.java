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
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
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

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_man", nullable = false)
    private Boolean isMan;

    @Column(name = "birth", nullable = false)
    private LocalDateTime birth;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "member_created_at", nullable = false)
    private LocalDateTime memberCreatedAt;

    @Column(name = "is_social", nullable = false)
    private Boolean isSocial;

    @Column(name = "is_writer")
    private Boolean isWriter;

    /**
     * 회원 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param membership   멤버십 정보
     * @param memberStatus 멤버 상태 정보 (정상회원, 차단회원, 탈퇴회원)
     * @param memberId     아이디
     * @param nickname     닉네임
     * @param name         이름
     * @param isMan        성별 여부
     * @param birth        생일
     * @param password     비밀번호
     * @param phoneNumber  핸드폰 번호
     * @param email        이메일
     * @author 강명관
     * @author 노수연
     */
    @SuppressWarnings("java:S107") // 회원 테이블의 입력 받아야 될 필드값이 많기 때문
    @Builder
    public Member(Membership membership, MemberStatus memberStatus, String memberId,
                  String nickname,
                  String name, Boolean isMan, LocalDateTime birth, String password,
                  String phoneNumber,
                  String email, LocalDateTime memberCreatedAt, Boolean isSocial, Boolean isWriter) {
        this.membership = membership;
        this.memberStatus = memberStatus;
        this.memberId = memberId;
        this.nickname = nickname;
        this.name = name;
        this.isMan = isMan;
        this.birth = birth;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memberCreatedAt = memberCreatedAt;
        this.isSocial = isSocial;
        this.isWriter = isWriter;
    }

}
