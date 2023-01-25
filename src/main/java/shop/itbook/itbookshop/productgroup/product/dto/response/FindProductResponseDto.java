package shop.itbook.itbookshop.productgroup.product.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author 이하늬
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FindProductResponseDto {

    private Long productNo;

    private String productName;

    private String simpleDescription;

    private String detailsDescription;

    private Boolean isSelled;

    private Boolean isDeleted;

    private Integer stock;

//    private String category;

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

    @Setter
    private Long selledPrice;

    @Setter
    String thumbnailsName;

    public FindProductResponseDto(Long productNo, String productName, String simpleDescription,
                                  String detailsDescription, Boolean isSelled, Boolean isDeleted,
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
        this.isSelled = isSelled;
        this.isDeleted = isDeleted;
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
