package shop.itbook.itbookshop.book.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 도서 엔티티입니다.
 *
 * @author 이하늬
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

    @Column(name = "isbn", nullable = false, columnDefinition = "varchar(30)", unique = true)
    private String isbn;

    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @Column(name = "book_created_at", nullable = false)
    private LocalDateTime bookCreatedAt;

    @Column(name = "is_ebook", nullable = false)
    private Boolean isEbook;

    @Column(name = "ebook_url", columnDefinition = "text")
    private String ebookUrl;

    @Column(name = "publisher_name", nullable = false, columnDefinition = "varchar(20)")
    private String publisherName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_no", nullable = false)
    private ProductType productType;

    @Builder
    public Book(Long productNo, Product product, String isbn, Integer pageCount,
                LocalDateTime bookCreatedAt, boolean isEbook, String tableOfContents,
                String ebookUrl,
                String publisherName, ProductType productType) {
        this.productNo = productNo;
        this.product = product;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.bookCreatedAt = bookCreatedAt;
        this.isEbook = isEbook;
        this.ebookUrl = ebookUrl;
        this.publisherName = publisherName;
        this.productType = productType;
    }
}
