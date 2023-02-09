package shop.itbook.itbookshop.category.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리를 위한 엔티티입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Setter
@Getter
@Table(name = "category")
@Entity
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Integer categoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_no")
    private Category parentCategory;

    @Column(name = "category_name", columnDefinition = "varchar(20)",
        unique = true, nullable = false)
    private String categoryName;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Builder
    public Category(Category parentCategory, String categoryName,
                    Boolean isHidden, Integer level, Integer sequence) {
        this.parentCategory = parentCategory;
        this.categoryName = categoryName;
        this.isHidden = isHidden;
        this.level = level;
        this.sequence = sequence;
    }
}
