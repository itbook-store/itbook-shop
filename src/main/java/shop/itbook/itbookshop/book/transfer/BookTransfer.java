package shop.itbook.itbookshop.book.transfer;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.entity.Book;

/**
 * 도서에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class BookTransfer {
    private BookTransfer() {
    }

    /**
     * dto로 넘어온 값을 도서 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param requestDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author 이하늬
     */
    public static Book dtoToEntityAdd(BookRequestDto requestDto, Long productNo) {
        LocalDateTime date = LocalDateTime.parse(requestDto.getBookCreatedAt());

        return Book.builder().isbn(requestDto.getIsbn()).pageCount(requestDto.getPageCount())
            .isEbook(requestDto.getIsEbook()).authorName(requestDto.getAuthorName())
            .bookCreatedAt(date).publisherName(requestDto.getPublisherName()).productNo(productNo)
            .build();
    }

}
