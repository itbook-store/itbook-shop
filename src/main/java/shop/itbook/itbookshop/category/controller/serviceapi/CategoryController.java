package shop.itbook.itbookshop.category.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;

/**
 * 카테고리 관련 요청을 받고 반환하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 관리자가 숨기지 않은 카테고리를 조회할때 요청을 받고 반환하는 기능을 담당하는 메서드 입니다.
     *
     * @author 최겸준
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryListResponseDto>>> categoryList(
        @PageableDefault
        Pageable pageable) {
        Page<CategoryListResponseDto> categoryListByNotEmployeePage =
            categoryService.findCategoryListByNotEmployee(pageable);

        CommonResponseBody<PageResponse<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                new PageResponse<>(categoryListByNotEmployeePage));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
