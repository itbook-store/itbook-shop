package shop.itbook.itbookshop.productgroup.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
public class KakaoApiService {
    @Value("${kakao.rest-api.key}")
    private String tenantId = "fcb81f74e379456b8ca0e091d351a7af";
    public void getBookDetails() {

    }
}
