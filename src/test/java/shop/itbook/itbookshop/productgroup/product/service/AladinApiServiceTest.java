package shop.itbook.itbookshop.productgroup.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KakaoApiService.class)
class AladinApiServiceTest {

    @Autowired
    KakaoApiService kakaoApiService;

    @Test
    void responseBookTest() throws JsonProcessingException {
        KakaoApiService.BookResponse bookDetails =
            kakaoApiService.getBookDetails("9788960777330");

        log.info(bookDetails.getItem().toString());
        Assertions.assertThat(bookDetails).isNotNull();
        Assertions.assertThat(bookDetails.getItem().get(0).getAuthor()).isEqualTo("김영한 (지은이)");
    }

}