package shop.itbook.itbookshop.membergroup.membership.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등급에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_no")
    private Integer membershipNo;

    @Column(name = "membership_grade", nullable = false,
        columnDefinition = "varchar(20)", unique = true)
    private String membershipGrade;

    @Column(name = "membership_standard_amount", nullable = false, unique = true)
    private Long membershipStandardAmount;

    @Column(name = "membership_point", nullable = false)
    private Long membershipPoint;


    /**
     * 회원 등급 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param membershipGrade          the membership grade
     * @param membershipStandardAmount the membership standard amount
     * @param membershipPoint          the membership point
     * @author 강명관
     */
    @Builder
    public Membership(String membershipGrade, Long membershipStandardAmount, Long membershipPoint) {
        this.membershipGrade = membershipGrade;
        this.membershipStandardAmount = membershipStandardAmount;
        this.membershipPoint = membershipPoint;
    }

    /**
     * 회원등급을 수정할 경우의 메서드 입니다.
     *
     * @param membershipGrade          the membership grade
     * @param membershipStandardAmount the membership standard amount
     * @param membershipPoint          the membership point
     * @author 강명관 *
     */
    public void updateMembershipInfo(String membershipGrade, Long membershipStandardAmount,
                                     Long membershipPoint) {

        if (Objects.nonNull(membershipGrade)) {
            this.membershipGrade = membershipGrade;
        }

        if (Objects.nonNull(membershipStandardAmount)) {
            this.membershipStandardAmount = membershipStandardAmount;
        }

        if (Objects.nonNull(membershipPoint)) {
            this.membershipPoint = membershipPoint;
        }

    }
}
