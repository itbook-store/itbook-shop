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
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 즐겨찾기 테이블에 대한 엔티티입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
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
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "bookmark_created_at", nullable = false)
    private LocalDateTime bookmarkCreatedAt;

    /**
     * 즐겨찾기 테이블에 대한 엔티티 생성자 입니다.
     *
     * @param member  the member
     * @param product the product
     * @author 강명관
     */
    public Bookmark(Member member, Product product) {
        this.member = member;
        this.product = product;
        this.bookmarkCreatedAt = LocalDateTime.now();
    }
}
