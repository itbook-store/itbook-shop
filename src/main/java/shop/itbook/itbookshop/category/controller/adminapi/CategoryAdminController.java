package shop.itbook.itbookshop.category.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;

/**
 * 관리자에 대한 요청을 받고 반환하는 컨트롤러 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    /**
     * 카테고리 저장 요청을 처리하는 메서드입니다.
     *
     * @param categoryRequestDto 저장을 위한 정보를 바인딩받는 객체입니다.
     * @return 저장한 카테고리의 번호를 ResponseEntity 에 담아 반환합니다
     * @author 최겸준
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<CategoryNoResponseDto>> categorySave(
        @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryNoResponseDto categoryNoResponseDto =
            new CategoryNoResponseDto(categoryAdminService.saveCategory(categoryRequestDto));

        CommonResponseBody<CategoryNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                CategoryResultMessageEnum.CATEGORY_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            categoryNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 카테고리의 모든 리스트를 조회하는 메서드입니다.
     *
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<List<CategoryResponseProjectionDto>>> categoryList() {

        CommonResponseBody<List<CategoryResponseProjectionDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                categoryAdminService.findCategoryList());

        return ResponseEntity.ok().body(commonResponseBody);
    }

    // TODO 1 : 카테고리 조회에서 hidden 과 nohidden 도 추가해야함 현재는 all 만 있음

    /**
     * 카테고리의 번호를 받아서 해당 카테고리의 자식카테고리들을 반환하는 요청을 처리하는 메서드입니다.
     *
     * @param categoryNo 부모카테고리의 번호입니다.
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/{categoryNo}/child-categories")
    public ResponseEntity<CommonResponseBody<List<CategoryChildResponseProjectionDto>>>
    categoryChildList(@PathVariable Integer categoryNo) {

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                CategoryResultMessageEnum.CATEGORY_CHILD_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
            categoryAdminService.findCategoryChildList(categoryNo)));
    }

    /**
     * 카테고리 세부정보 조회 요청을 받고 반환하는 메서드입니다.
     *
     * @param categoryNo 세부조회할 카테고리의 번호입니다.
     * @return 카테고리의 세부정보를 ResponseEntity에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<CategoryResponseDto>> categoryDetails(
        @PathVariable Integer categoryNo) {

        CommonResponseBody<CategoryResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                CategoryResultMessageEnum.CATEGORY_DETAILS_SUCCESS_MESSAGE.getSuccessMessage()),
            categoryAdminService.findCategoryResponseDtoThroughCategoryNo(categoryNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
