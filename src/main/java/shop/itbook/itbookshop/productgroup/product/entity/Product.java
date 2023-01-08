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

/**
 * 상품에 대한 엔티티 입니다.
 *
 * @author 노수연
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
    private boolean isSelled;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "is_subscription", nullable = false)
    private boolean isSubscription;

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
    private Integer discountPercent;

    @Column(name = "raw_price", nullable = false)
    private Long rawPrice;

    @SuppressWarnings("java:S107") // 생성자 필드 갯수가 많아 추가
    @Builder
    public Product(String name, String simpleDescription, String detailsDescription, Integer stock,
                   LocalDateTime productCreatedAt, boolean isSelled, boolean isDeleted,
                   boolean isSubscription, String thumbnailUrl, Long dailyHits, Long fixedPrice,
                   Integer increasePointPercent, Integer discountPercent, Long rawPrice) {
        this.name = name;
        this.simpleDescription = simpleDescription;
        this.detailsDescription = detailsDescription;
        this.stock = stock;
        this.productCreatedAt = productCreatedAt;
        this.isSelled = isSelled;
        this.isDeleted = isDeleted;
        this.isSubscription = isSubscription;
        this.thumbnailUrl = thumbnailUrl;
        this.dailyHits = dailyHits;
        this.fixedPrice = fixedPrice;
        this.increasePointPercent = increasePointPercent;
        this.discountPercent = discountPercent;
        this.rawPrice = rawPrice;
    }

}
