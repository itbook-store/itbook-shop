package shop.itbook.itbookshop.book.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.book.dto.response.FindBookListResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;

/**
 * 상품 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface BookAdminService {

    /**
     * 도서 등록을 담당하는 메서드입니다.
     *
     * @param productNo  도서 등록을 위한 상품 번호입니다.
     * @param requestDto 도서 등록을 위한 정보를 담은 dto 객체입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addBook(AddProductBookRequestDto requestDto, Long productNo);

    List<FindBookListResponseDto> findBookList();

    Long addBook(AddProductBookRequestDto requestDto);

    /**
     * 상품 수정을 담당하는 메서드입니다.
     *
     * @param productNo  수정할 상품 번호입니다.
     * @param requestDto 상품 수정을 위한 정보를 담은 dto 객체입니다.
     * @author 이하늬
     */
}
