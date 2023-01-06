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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.product.entity.Product;
import shop.itbook.itbookshop.producttype.entity.ProductType;

/**
 * 도서 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Builder
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
    private boolean isEbook;

    @Column(name = "table_of_contents", nullable = true, columnDefinition = "text")
    private String tableOfContents;

    @Column(name = "ebook_url", nullable = true, columnDefinition = "text")
    private String ebookUrl;

    @Column(name = "publisher_name", nullable = false, columnDefinition = "varchar(20)")
    private String publisherName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_no", nullable = false)
    private ProductType productType;
}
