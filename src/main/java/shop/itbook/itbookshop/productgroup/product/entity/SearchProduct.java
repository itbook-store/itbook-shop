package shop.itbook.itbookshop.productgroup.product.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * 상품에 대한 도큐먼트 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "#{@environment.getProperty('elastic.index.product')}")
@Setting(settingPath = "elastic/product-setting.json")
@Mapping(mappingPath = "elastic/product-mapping.json")
public class SearchProduct {

    @Id
    @Column
    private Long productNo;

    @Column
    private String name;

    @Column
    private String simpleDescription;

    @Column
    private String detailsDescription;

    @Column
    private Integer stock;

    @Column
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime productCreatedAt;

    @Column
    private Boolean isSelled;

    @Column
    private Boolean isDeleted;

    @Column
    private String thumbnailUrl;

    @Column
    private Long dailyHits;

    @Column
    private Long fixedPrice;

    @Column
    private Integer increasePointPercent;

    @Column
    private Double discountPercent;

    @Column
    private Long rawPrice;

    /**
     * 빌터 패턴을 적용한 생성자입니다.
     *
     * @param name                 상품명입니다.
     * @param simpleDescription    상품 간단 설명입니다.
     * @param detailsDescription   상품 상세 설명입니다.
     * @param stock                상품 재고입니다.
     * @param productCreatedAt     상품 등록 일자입니다.
     * @param isSelled             상품 판매 여부입니다.
     * @param isDeleted            상품 삭제 여부입니다.
     * @param thumbnailUrl         상품 썸네일 url 입니다.
     * @param dailyHits            상품 조회수입니다.
     * @param fixedPrice           상품 정가입니다.
     * @param increasePointPercent 상품 포인트 적립율입니다.
     * @param discountPercent      상품 할인율입니다.
     * @param rawPrice             상품 매입원가입니다.
     * @author 송다혜
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public SearchProduct(Long productNo, String name, String simpleDescription,
                         String detailsDescription, Integer stock,
                         LocalDateTime productCreatedAt, Boolean isSelled, Boolean isDeleted,
                         String thumbnailUrl, Long dailyHits, Long fixedPrice,
                         Integer increasePointPercent, Double discountPercent, Long rawPrice) {
        this.productNo = productNo;
        this.name = name;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.stock = stock;
        this.productCreatedAt = productCreatedAt;
        this.isSelled = isSelled;
        this.isDeleted = isDeleted;
        this.thumbnailUrl = thumbnailUrl;
        this.dailyHits = dailyHits;
        this.fixedPrice = fixedPrice;
        this.increasePointPercent = increasePointPercent;
        this.discountPercent = discountPercent;
        this.rawPrice = rawPrice;
    }

}