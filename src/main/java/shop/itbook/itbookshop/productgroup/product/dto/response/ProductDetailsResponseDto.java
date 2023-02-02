package shop.itbook.itbookshop.productgroup.product.dto.response;

import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 상세정보를 담은 dto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailsResponseDto {

    private Long productNo;

    private String productName;

    private String simpleDescription;

    private String detailsDescription;

    private Boolean isExposed;

    private Boolean isForceSoldOut;

    private Integer stock;

    private Integer increasePointPercent;

    private Long rawPrice;

    private Long fixedPrice;

    private Double discountPercent;

    private String fileThumbnailsUrl;

    private String isbn;

    private Integer pageCount;

    private LocalDateTime bookCreatedAt;

    private Boolean isEbook;

    private String fileEbookUrl;

    private String publisherName;

    private String authorName;

    private Boolean isPointApplyingBasedSellingPrice;

    private Boolean isPointApplying;

    private Boolean isSubscription;

    @Setter
    private Long selledPrice;

    @Setter
    String thumbnailsName;

    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public ProductDetailsResponseDto(Long productNo, String productName, String simpleDescription,
                                     String detailsDescription, Boolean isExposed,
                                     Boolean isForceSoldOut,
                                     Integer stock, Integer increasePointPercent, Long rawPrice,
                                     Long fixedPrice, Double discountPercent,
                                     String fileThumbnailsUrl,
                                     String isbn, Integer pageCount, LocalDateTime bookCreatedAt,
                                     Boolean isEbook, String fileEbookUrl, String publisherName,
                                     String authorName) {
        this.productNo = productNo;
        this.productName = productName;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.isExposed = isExposed;
        this.isForceSoldOut = isForceSoldOut;
        this.stock = stock;
        this.increasePointPercent = increasePointPercent;
        this.rawPrice = rawPrice;
        this.fixedPrice = fixedPrice;
        this.discountPercent = discountPercent;
        this.fileThumbnailsUrl = fileThumbnailsUrl;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.bookCreatedAt = bookCreatedAt;
        this.isEbook = isEbook;
        this.fileEbookUrl = fileEbookUrl;
        this.publisherName = publisherName;
        this.authorName = authorName;
    }
}
