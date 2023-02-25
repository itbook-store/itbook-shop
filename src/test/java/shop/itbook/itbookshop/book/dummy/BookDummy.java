package shop.itbook.itbookshop.book.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
public class BookDummy {

    public static Book getBookSuccess() {

        return Book.builder()
            .isbn("0192390184")
            .bookCreatedAt(LocalDateTime.now())
            .productNo(1L)
            .publisherName("출판사")
            .authorName("작가")
            .pageCount(10)
            .isEbook(Boolean.FALSE)
            .ebookUrl("testUrl")
            .build();
    }

    public static Book getBookFailure() {

        return Book.builder()
            .isbn(null)
            .bookCreatedAt(LocalDateTime.now())
            .productNo(1L)
            .publisherName("출판사")
            .authorName("작가")
            .pageCount(10)
            .isEbook(Boolean.FALSE)
            .ebookUrl("testUrl")
            .build();
    }

    public static BookModifyRequestDto getBookModifyRequest() {

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(4);

        return BookModifyRequestDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .categoryNoList(categoryList)
            .increasePointPercent(1.0)
            .rawPrice(12000L)
            .fixedPrice(20000L)
            .discountPercent(10.0)
            .isbn("1111111111")
            .pageCount(10)
            .bookCreatedAt(LocalDate.now().toString())
            .isEbook(Boolean.TRUE)
            .publisherName("publisher12")
            .authorName("author12")
//            .fileThumbnailsUrl("thumbnailsUrl")
            .fileThumbnailsUrl(null)
            .fileEbookUrl("tel")
            .isPointApplyingBasedSellingPrice(true)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

    public static BookDetailsResponseDto getBookDetailsResponseDto() {

        return BookDetailsResponseDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .stock(1)
            .increasePointPercent(1.0)
            .rawPrice(12000L)
            .fixedPrice(20000L)
            .discountPercent(10.0)
            .isbn("testIsbn")
            .pageCount(10)
            .bookCreatedAt(LocalDateTime.now())
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
