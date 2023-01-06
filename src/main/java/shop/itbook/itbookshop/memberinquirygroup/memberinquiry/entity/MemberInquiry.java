package shop.itbook.itbookshop.memberinquirygroup.memberinquiry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.member.entity.Member;
import shop.itbook.itbookshop.memberinquirygroup.inquirytype.entity.InquiryType;

/**
 * 고객문의에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_inquiry")
@Entity
public class MemberInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_inquiry_no", nullable = false)
    private Long memberInquiryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_member_inquiry_no", nullable = false)
    private MemberInquiry parentMemberInquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_type_no", nullable = false)
    private InquiryType inquiryType;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    /**
     * 회원문의 생성자입니다.
     *
     * @param parentMemberInquiry the parent member inquiry
     * @param member              the member
     * @param inquiryType         the inquiry type
     * @param title               the title
     * @param content             the content
     * @author 이하늬
     */
    @Builder
    public MemberInquiry(MemberInquiry parentMemberInquiry, Member member, InquiryType inquiryType,
                         String title, String content) {
        this.parentMemberInquiry = parentMemberInquiry;
        this.member = member;
        this.inquiryType = inquiryType;
        this.title = title;
        this.content = content;
    }
}
