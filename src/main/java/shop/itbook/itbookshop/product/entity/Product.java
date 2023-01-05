package shop.itbook.itbookshop.product.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(name = "product_no", nullable = false)
    private Long productNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "simple_description", nullable = false)
    private String simpleDescription;

    @Column(name = "details_description")
    private String detailsDescription;

    @Column(name = "stock", nullable = false)
    @ColumnDefault("0")
    private Integer stock;

    @Column(name = "product_created_at", nullable = false)
    private LocalDateTime productCreatedAt;

    @Column(name = "is_selled", nullable = false)
    private Boolean isSelled;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "is_subscription", nullable = false)
    private Boolean isSubscription;

    @Column(name = "thumnail_url", nullable = false)
    private String thumnailUrl;

    @Column(name = "daily_hits", nullable = false)
    @ColumnDefault("0")
    private Long dailyHits;

    @Column(name = "fixed_price", nullable = false)
    private Long fixedPrice;

    @Column(name = "point_saving_percent", nullable = false)
    @ColumnDefault("0")
    private Integer pointSavingPercent;

    @Column(name = "discount_percent", nullable = false)
    @ColumnDefault("0")
    private Integer discountPercent;

    @Column(name = "raw_price", nullable = false)
    private Long rawPrice;
}
