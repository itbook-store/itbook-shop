package shop.itbook.itbookshop.productgroup.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class Item {


    private SubInfo subInfo;
    private String title;
    private String author;
    private String pubDate;
    private String description;
    private Long priceStandard;
    private String publisher;

}