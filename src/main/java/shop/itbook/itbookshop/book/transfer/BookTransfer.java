package shop.itbook.itbookshop.book.transfer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;

/**
 * 도서에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoArgsConstructor
public class BookTransfer {
    /**
     * dto로 넘어온 값을 도서 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param requestDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author 이하늬
     */
    public static Book dtoToEntityAdd(ProductBookRequestDto requestDto, Long productNo) {
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(requestDto.getBookCreatedAt(), DATEFORMATTER);
        LocalDateTime date = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        return Book.builder()
            .isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount())
            .isEbook(requestDto.getIsEbook())
            .authorName(requestDto.getAuthorName())
            .bookCreatedAt(date)
            .publisherName(requestDto.getPublisherName())
            .productNo(productNo)
            .build();
    }

}
