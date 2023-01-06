package shop.itbook.itbookshop.bookmark.entity;

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
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.product.entity.Product;

/**
 * 장바구니 테이블에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookmark", uniqueConstraints = {
    @UniqueConstraint(name = "UniqueProductNoAndMemberNo",
        columnNames = {"product_no", "member_no"})
})
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_no")
    private Long bookmarkNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "bookmark_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime bookmarkCreatedAt;

    /**
     * 즐겨찾기 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param product the product
     * @param member  the member
     * @author 강명관
     */
    @Builder
    public Bookmark(Product product, Member member) {
        this.product = product;
        this.member = member;
    }
}
