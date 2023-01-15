package shop.itbook.itbookshop.productgroup.product.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * 상품에 대한 엔티티 입니다.
 *
 * @author 노수연
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
@Document(indexName = "itbook_product_nori_test1")
@Setting(settingPath = "elastic/product-setting.json")
public class Product {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no", nullable = false)
    private Long productNo;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(name = "simple_description", nullable = false)
    private String simpleDescription;

    @Column(name = "details_description", columnDefinition = "text")
    private String detailsDescription;

    @Column(name = "stock", nullable = false, columnDefinition = "integer default 0")
    private Integer stock;

    @Column(name = "product_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime productCreatedAt;

    @Column(name = "is_selled", nullable = false)
    private Boolean isSelled;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "thumbnail_url", nullable = false, columnDefinition = "text")
    private String thumbnailUrl;

    @Column(name = "daily_hits", nullable = false, columnDefinition = "bigint default 0")
    private Long dailyHits;

    @Column(name = "fixed_price", nullable = false)
    private Long fixedPrice;

    @Column(name = "increase_point_percent", nullable = false,
        columnDefinition = "integer default 0")
    private Integer increasePointPercent;

    @Column(name = "discount_percent", nullable = false, columnDefinition = "integer default 0")
    private Double discountPercent;

    @Column(name = "raw_price", nullable = false)
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
     * @param thumbnailUrl         상품 썸네일 url입니다.
     * @param dailyHits            상품 조회수입니다.
     * @param fixedPrice           상품 정가입니다.
     * @param increasePointPercent 상품 포인트 적립율입니다.
     * @param discountPercent      상품 할인율입니다.
     * @param rawPrice             상품 매입원가입니다.
     * @author 이하늬
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Product(String name, String simpleDescription, String detailsDescription, Integer stock,
                   LocalDateTime productCreatedAt, Boolean isSelled, Boolean isDeleted,
                   String thumbnailUrl, Long dailyHits, Long fixedPrice,
                   Integer increasePointPercent, Double discountPercent, Long rawPrice) {
        this.name = name;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.stock = stock;
        this.productCreatedAt = LocalDateTime.now();
        this.isSelled = isSelled;
        this.isDeleted = isDeleted;
        this.thumbnailUrl = thumbnailUrl;
        this.dailyHits = 0L;
        this.fixedPrice = fixedPrice;
        this.increasePointPercent = increasePointPercent;
        this.discountPercent = discountPercent;
        this.rawPrice = rawPrice;
    }

}
