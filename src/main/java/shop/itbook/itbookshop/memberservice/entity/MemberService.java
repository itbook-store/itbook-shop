package shop.itbook.itbookshop.memberservice.entity;

import java.time.LocalDateTime;
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
import shop.itbook.itbookshop.servicetype.entity.ServiceType;

/**
 * 회원서비스에 대한 엔티티입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_service")
@Entity
public class MemberService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_service_no")
    private Long memberServiceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_no", nullable = false)
    private ServiceType serviceType;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime createdAt;

    @Builder
    public MemberService(Member member, ServiceType serviceType, String title, String content) {
        this.member = member;
        this.serviceType = serviceType;
        this.title = title;
        this.content = content;
    }
}
