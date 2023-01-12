package shop.itbook.itbookshop.category.controller.serviceapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;

/**
 * 카테고리 관련 요청을 받고 반환하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    /**
     * 관리자가 숨기지 않은 카테고리를 조회할때 요청을 받고 반환하는 기능을 담당하는 메서드 입니다.
     *
     * @param categoryNo 조회해야할 카테고리의 번호를 의미합니다.
     * @author 최겸준
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<CategoryAllFieldResponseDto>> categoryDetails(
        @PathVariable Integer categoryNo) {

        return null;
    }
}
