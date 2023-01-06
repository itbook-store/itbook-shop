package shop.itbook.itbookshop.memberinquirygroup.memberinquiryregistration.entity;

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
import shop.itbook.itbookshop.memberinquirygroup.memberinquiry.entity.MemberInquiry;

/**
 * 회원문의등록에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_inquiry_registration")
public class MemberInquiryRegistration {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @MapsId("memberInquiryNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_inquiry_no", nullable = false)
    private MemberInquiry memberInquiry;

    /**
     * MemberInquiryRegistration 엔티티를 식별하기 위한 PK 클래스입니다.
     *
     * @author 이하늬
     * @since 1.0
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        private Long memberNo;

        private Long memberInquiryNo;
    }

}
