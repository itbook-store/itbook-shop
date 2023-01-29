package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import lombok.Data;

/**
 * 오브젝트 스토리지 토큰에 대한 클래스입니다.
 *
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
