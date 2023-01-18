package shop.itbook.itbookshop.book.service.adminapi;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
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
     * @param requestDto 도서 등록을 위한 정보를 담은 dto 객체입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addBook(AddProductBookRequestDto requestDto, MultipartFile thumbnails,
                 MultipartFile ebook);

    List<FindBookResponseDto> findBookList();

    FindBookResponseDto findBook(Long id);
}
