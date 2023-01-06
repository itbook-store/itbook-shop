package shop.itbook.itbookshop.bookauthor.entity;

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
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 도서작가 엔티티입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_author")
@Entity
public class BookAuthor {

    @EmbeddedId
    private Pk pk;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Book book;

    @MapsId("memberNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    /**
     * BookAuthor 엔티티를 식별하기 위한 PK 클래스입니다.
     *
     * @author 이하늬 * @since 1.0
     */
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Pk implements Serializable {

        private Long productNo;

        private Long memberNo;
    }
}
