package shop.itbook.itbookshop.book.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 도서 상세 정보를 조회하기 위한 ResponseDto 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDetailsResponseDto {

    private Long productNo;

    private String productName;

    private String simpleDescription;

    private String detailsDescription;

    private Boolean isSelled;

    private Boolean isForceSoldOut;
    private Boolean isPointApplying;

    private Integer stock;

    private Double increasePointPercent;

    private Long rawPrice;

    private Long fixedPrice;

    private Double discountPercent;

    private String fileThumbnailsUrl;

    private String isbn;

    private Integer pageCount;

    private LocalDateTime bookCreatedAt;

    private Boolean isEbook;
    private Boolean isSubscription;
    private Boolean isPointApplyingBasedSellingPrice;

    private String fileEbookUrl;

    private String publisherName;

    private String authorName;

    @Setter
    private Long selledPrice;

    @Setter
    String thumbnailsName;

    @Builder
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    public BookDetailsResponseDto(Long productNo, String productName, String simpleDescription,
                                  String detailsDescription, Boolean isSelled,
                                  Boolean isForceSoldOut, Boolean isSubscription,
                                  Integer stock, Double increasePointPercent, Long rawPrice,
                                  Long fixedPrice, Double discountPercent,
                                  String fileThumbnailsUrl, Boolean isPointApplying,
                                  String isbn, Integer pageCount, LocalDateTime bookCreatedAt,
                                  Boolean isEbook, String fileEbookUrl, String publisherName,
                                  String authorName, Boolean isPointApplyingBasedSellingPrice) {
        this.productNo = productNo;
        this.productName = productName;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.isSelled = isSelled;
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
        this.isPointApplyingBasedSellingPrice = isPointApplyingBasedSellingPrice;
        this.isSubscription = isSubscription;
        this.isPointApplying = isPointApplying;
    }
}
