package shop.itbook.itbookshop.category.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;

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
    private static final Boolean SUCCEED = Boolean.TRUE;

    /**
     * 관리자가 숨기지 않은 카테고리를 조회할때 요청을 받고 반환하는 기능을 담당하는 메서드 입니다.
     *
     * @author 최겸준
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>> categoryList() {
        log.info("####### 카테고리리스트 메소드 시작");
        CommonResponseBody<List<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(SUCCEED, HttpStatus.OK.value(),
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                categoryService.findCategoryListByNotEmployee());

        log.info("####### 카테고리리스트 메소드 종료");
        return ResponseEntity.ok().body(commonResponseBody);
    }
}
