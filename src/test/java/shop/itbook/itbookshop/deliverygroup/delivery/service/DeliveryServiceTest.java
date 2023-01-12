package shop.itbook.itbookshop.deliverygroup.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.config.RestTemplateConfig;
import shop.itbook.itbookshop.deliverygroup.delivery.service.impl.DeliveryServiceImpl;

/**
 * @author 정재원
 * @since 1.0
 */
class DeliveryServiceTest {

    @Test
    @DisplayName("더미 서버에 등록 요청 성공")
    void addDeliveryToDummyServerTest() {
    }
}