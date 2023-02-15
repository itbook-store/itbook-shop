package shop.itbook.itbookshop.productgroup.product.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;

/**
 * 상품에 대한 엔티티 입니다.
 *
 * @author 노수연 * @since 1.0
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no", nullable = false)
    private Long productNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "simple_description", nullable = false)
    private String simpleDescription;

    @Column(name = "details_description")
    private String detailsDescription;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "product_created_at", nullable = false)
    private LocalDateTime productCreatedAt;

    @Column(name = "is_exposed", nullable = false)
    private Boolean isSelled;

    @Column(name = "is_force_sold_out", nullable = false)
    private Boolean isForceSoldOut;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Column(name = "daily_hits", nullable = false)
    private Long dailyHits;

    @Column(name = "fixed_price", nullable = false)
    private Long fixedPrice;

    @Column(name = "increase_point_percent", nullable = false,
        columnDefinition = "integer default 0")
    private Integer increasePointPercent;

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "raw_price", nullable = false)
    private Long rawPrice;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private Book book;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductCategory> productCategoryList;

    @Column(name = "is_point_applying_based_selling_price")
    private Boolean isPointApplyingBasedSellingPrice;

    @Column(name = "is_point_applying", nullable = false)
    private Boolean isPointApplying;

    @Column(name = "is_subscription", nullable = false)
    private Boolean isSubscription;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    /**
     * 빌더 패턴을 적용한 생성자입니다.
     *
     * @param name                 상품명입니다.
     * @param simpleDescription    상품 간단 설명입니다.
     * @param detailsDescription   상품 상세 설명입니다.
     * @param stock                상품 재고입니다.
     * @param productCreatedAt     상품 등록 일자입니다.
     * @param isSelled             상품 판매 여부입니다.
     * @param isForceSoldOut       상품 삭제 여부입니다.
     * @param thumbnailUrl         상품 썸네일 url입니다.
     * @param fixedPrice           상품 정가입니다.
     * @param increasePointPercent 상품 포인트 적립율입니다.
     * @param discountPercent      상품 할인율입니다.
     * @param rawPrice             상품 매입원가입니다.
     * @author 이하늬
     */
    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Product(String name, String simpleDescription, String detailsDescription, Integer stock,
                   LocalDateTime productCreatedAt, Boolean isSelled, Boolean isForceSoldOut,
                   String thumbnailUrl, Long fixedPrice, Integer increasePointPercent,
                   Integer discountPercent, Long rawPrice, Boolean isPointApplyingBasedSellingPrice,
                   Boolean isPointApplying, Boolean isSubscription) {
        this.name = name;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.stock = stock;
        this.isSelled = isSelled;
        this.isForceSoldOut = isForceSoldOut;
        this.thumbnailUrl = thumbnailUrl;
        this.fixedPrice = fixedPrice;
        this.increasePointPercent = increasePointPercent;
        this.discountPercent = discountPercent;
        this.rawPrice = rawPrice;
        this.isPointApplyingBasedSellingPrice = isPointApplyingBasedSellingPrice;
        this.isPointApplying = isPointApplying;
        this.isSubscription = isSubscription;
        this.productCreatedAt = productCreatedAt;
        this.dailyHits = 0L;
        this.isDeleted = false;
    }

}
