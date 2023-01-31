package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import java.io.Serializable;
import lombok.Data;

@Data
public class Access implements Serializable {
    private Token token;
}
