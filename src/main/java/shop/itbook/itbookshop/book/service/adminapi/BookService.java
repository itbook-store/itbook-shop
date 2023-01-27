package shop.itbook.itbookshop.book.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;

/**
 * 도서 Service 인터페이스입니다.
 * 도서는 상품에 대해 약개체로 존재합니다.
 * 따라서, 도서는 상품 번호를 Fk이자 Pk로 사용합니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface BookService {

    /**
     * 도서 전체를 조회하는 메서드입니다.
     *
     * @return 도서에 대한 모든 정보를 담아 리스트로 반환합니다.
     * @author 이하늬
     */
    List<BookDetailsResponseDto> findBookList();

    /**
     * 상품이 도서일 경우 상품 번호를 받아 추가적으로 도서 정보를 등록하는 기능을 담당합니다.
     *
     * @param requestDto 도서 등록에 필요한 정보를 담은 dto 객체입니다.
     * @param productNo  추가 정보를 등록할 도서(상품) 번호입니다.
     * @return 등록한 도서(상품) 번호를 반환합니다.
     * @author 이하늬
     */
    Long addBook(BookRequestDto requestDto, Long productNo);

    /**
     * 도서를 조회해 도서의 상세 정보를 담아 반환하는 기능을 담당합니다.
     *
     * @param productNo 조회할 도서(상품) 번호입니다.
     * @return 조회한 도서 상세 정보를 반환합니다.
     * @author 이하늬
     */
    BookDetailsResponseDto findBook(Long productNo);

    /**
     * 도서를 조회해 도서 엔티티를 반환하는 기능을 담당합니다.
     *
     * @param productNo 조회할 도서(상품) 번호입니다.
     * @return 조회한 도서 엔티티를 반환합니다.
     * @author 이하늬
     */
    Book findBookEntity(Long productNo);

    /**
     * 상품과 도서의 정보가 있는 dto 객체를 도서 정보만 가지는 dto로 변환하여 반환하는 기능을 담당합니다.
     *
     * @param requestDto 상품과 도서의 정보가 담긴 dto 객체입니다.
     * @return 도서 정보만을 담은 requestDto를 반환합니다.
     * @author 이하늬
     */
    BookRequestDto toBookRequestDto(ProductBookRequestDto requestDto);

    /**
     * 도서 정보를 수정하는 기능을 담당합니다.
     *
     * @param requestDto 수정할 도서 정보가 담긴 dto 객체입니다.
     * @param productNo  수정한 도서(상품) 번호입니다.
     * @author 이하늬
     */
    void modifyBook(BookRequestDto requestDto, Long productNo);
}
