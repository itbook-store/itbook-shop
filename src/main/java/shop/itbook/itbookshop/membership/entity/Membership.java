package shop.itbook.itbookshop.membership.entity;

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
import lombok.Setter;

/**
 * 회원 등급에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_no")
    private Integer memberNo;

    @Column(name = "membership_grade", nullable = false, columnDefinition = "varchar(20)")
    private String membershipGrade;

    @Column(name = "membership_standard_amount", nullable = false)
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
}
