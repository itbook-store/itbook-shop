package shop.itbook.itbookshop.productgroup.product.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductBookRequestDummy {
    public static ProductBookRequestDto getProductBookRequest() {

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(4);

        return ProductBookRequestDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .stock(1)
            .categoryNoList(categoryList)
            .increasePointPercent(1.0)
            .rawPrice(12000L)
            .fixedPrice(20000L)
            .discountPercent(10.0)
            .isbn("1111111111")
            .pageCount(10)
            .bookCreatedAt(LocalDate.now().toString())
            .isEbook(false)
            .publisherName("publisher")
            .authorName("author")
            .fileThumbnailsUrl("thumbnailsUrl")
            .fileEbookUrl(null)
            .isPointApplyingBasedSellingPrice(true)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

}
