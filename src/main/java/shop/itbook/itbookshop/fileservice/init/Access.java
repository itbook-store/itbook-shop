package shop.itbook.itbookshop.fileservice.init;

import java.io.Serializable;
import lombok.Data;

@Data
public class Access implements Serializable {
    private Token token;
}
