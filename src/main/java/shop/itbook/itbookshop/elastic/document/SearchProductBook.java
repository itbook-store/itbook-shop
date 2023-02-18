package shop.itbook.itbookshop.elastic.document;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
//@Document(indexName = "#{@environment.getProperty('elastic.index.book')}")
//@Mapping(mappingPath = "elastic/total-mapping.json")
public class SearchProductBook {

    @Id
    @Column
    private Long productNo;

    @Column
    private String name;
    @Column
    private String name_jaso;

    @Column
    private String simpleDescription;

    @Column
    private String thumbnailUrl;

    @Column
    private Long fixedPrice;

    @Column
    private Double discountPercent;

    @Column
    private Long rawPrice;

    @Column
    private String isbn;

    @Column
    private Boolean isEbook;

    @Column
    private String publisherName;

    @Column
    private String authorName;

    @Builder
    public SearchProductBook(Long productNo, String name, String simpleDescription,
                             String thumbnailUrl,
                             Long fixedPrice, Double discountPercent, Long rawPrice, String isbn,
                             Boolean isEbook, String publisherName, String authorName) {
        this.productNo = productNo;
        this.name = name;
        this.simpleDescription = simpleDescription;
        this.thumbnailUrl = thumbnailUrl;
        this.fixedPrice = fixedPrice;
        this.discountPercent = discountPercent;
        this.rawPrice = rawPrice;
        this.isbn = isbn;
        this.isEbook = isEbook;
        this.publisherName = publisherName;
        this.authorName = authorName;
    }
}
