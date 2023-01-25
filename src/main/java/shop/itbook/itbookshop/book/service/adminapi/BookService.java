package shop.itbook.itbookshop.book.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.book.dto.request.AddBookRequestDto;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;

/**
 * 상품 Service 인터페이스입니다.
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
    List<FindBookResponseDto> findBookList();

    /**
     * @param requestDto 도서 등록에 필요한 정보를 담은 dto 객체입니다.
     * @param productNo  the product no
     * @return the long
     * @author
     */
    Long addBook(AddBookRequestDto requestDto, Long productNo);

    /**
     * @param id the id
     * @return the find book response dto
     * @author
     */
    FindBookResponseDto findBook(Long id);

    AddBookRequestDto toBookRequestDto(ProductBookRequestDto requestDto);
}
