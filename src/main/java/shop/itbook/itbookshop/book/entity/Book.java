package shop.itbook.itbookshop.book.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 도서 엔티티입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "book")
@Entity
public class Book {

    @Id
    private Long productNo;

    @MapsId("productNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @Column(name = "book_created_at", nullable = false)
    private LocalDateTime bookCreatedAt;

    @Column(name = "is_ebook", nullable = false)
    private Boolean isEbook;

    @Column(name = "ebook_url", columnDefinition = "text")
    private String ebookUrl;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    /**
     * 빌더 패턴을 적용한 생성자입니다.
     *
     * @param productNo     상품 번호입니다.
     * @param product       상품입니다.
     * @param isbn          도서 isbn입니다.
     * @param pageCount     도서 페이지 수 입니다.
     * @param bookCreatedAt 도서 발간일입니다.
     * @param isEbook       도서 전자책 여부입니다.
     * @param ebookUrl      도서 전자책 url입니다.
     * @param publisherName 도서 출판사명 입니다.
     * @param authorName    도서 저자명 입니다.
     * @author
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Book(Long productNo, Product product, String isbn, Integer pageCount,
                LocalDateTime bookCreatedAt, Boolean isEbook,
                String ebookUrl, String publisherName, String authorName) {
        this.productNo = productNo;
        this.product = product;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.bookCreatedAt = bookCreatedAt;
        this.isEbook = isEbook;
        this.ebookUrl = ebookUrl;
        this.publisherName = publisherName;
        this.authorName = authorName;
    }
}
