package shop.itbook.itbookshop.productgroup.product.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {

    private List<Item> item;
}
