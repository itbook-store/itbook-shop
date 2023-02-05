package shop.itbook.itbookshop.productgroup.product.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.productgroup.product.dto.response.BookResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.Item;

/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AladinApiService.class)
class AladinApiServiceTest {

    @Autowired
    AladinApiService aladinApiService;

    @Test
    void responseBookTest_success() {
        Item bookDetails =
            aladinApiService.getBookDetails("9788998139766");

        log.info(bookDetails.toString());
        Assertions.assertThat(bookDetails).isNotNull();
        Assertions.assertThat(bookDetails.getAuthor()).isEqualTo("조영호 (지은이)");
        Assertions.assertThat(bookDetails.getPublisher()).isEqualTo("위키북스");
        Assertions.assertThat(bookDetails.getPubDate()).isEqualTo("2015-06-17");
        Assertions.assertThat(bookDetails.getPriceStandard()).isEqualTo(20000L);
        Assertions.assertThat(bookDetails.getTitle())
            .isEqualTo("객체지향의 사실과 오해 - 역할, 책임, 협력 관점에서 본 객체지향");
        Assertions.assertThat(bookDetails.getDescription())
            .isEqualTo(
                "위키북스 IT Leaders 시리즈 23권. 객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.");
    }

    @Test
    void responseBookTest_failure() {
        Item bookDetails =
            aladinApiService.getBookDetails("97889981397666");

        Assertions.assertThat(bookDetails).isNull();
    }

}