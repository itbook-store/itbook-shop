package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import lombok.Data;

/**
 * @author 이하늬
 * @since 1.0
 */
@Data
public class ItBookObjectStorageToken {
    private Access access;

    @Data
    static class Access {
        private Token token;
    }

    @Data
    public static class Token {
        private String id;
        private String expires;
    }

}
