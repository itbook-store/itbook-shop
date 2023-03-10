package shop.itbook.itbookshop.category.controller.adminapi;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;

/**
 * 관리자에 대한 요청을 받고 반환하는 컨트롤러 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Validated
public class CategoryAdminController {

    private final CategoryService categoryService;

    /**
     * 카테고리 저장 요청을 처리하는 메서드입니다.
     *
     * @param categoryRequestDto 저장을 위한 정보를 바인딩받는 객체입니다.
     * @return 저장한 카테고리의 번호를 ResponseEntity 에 담아 반환합니다
     * @author 최겸준 *
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<CategoryNoResponseDto>> categoryAdd(
        @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryNoResponseDto categoryNoResponseDto =
            new CategoryNoResponseDto(categoryService.addCategory(categoryRequestDto));

        CommonResponseBody<CategoryNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            categoryNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 카테고리의 모든 리스트를 조회하는 메서드입니다.
     *
     * @param pageable 페이징을 위한 객체
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준 *
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryListResponseDto>>> categoryList(
        @PageableDefault
        Pageable pageable) {

        Page<CategoryListResponseDto> page =
            categoryService.findCategoryListByEmployee(pageable);

        PageResponse<CategoryListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Main category list response entity.
     *
     * @param pageable 페이징을 위한 객체
     * @return the response entity
     * @author 정재원 *
     */
    @GetMapping("/main-categories")
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryListResponseDto>>> mainCategoryList(
        @PageableDefault Pageable pageable) {

        Page<CategoryListResponseDto> mainCategoryListPage =
            categoryService.findMainCategoryList(pageable);

        CommonResponseBody<PageResponse<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                new PageResponse<>(mainCategoryListPage));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 부모카테고리의 번호를 받아서 해당 카테고리의 자식카테고리들을 반환하는 요청을 처리하는 메서드입니다.
     *
     * @param categoryNo 부모카테고리의 번호입니다.
     * @param pageable   the pageable
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준 *
     */
    @GetMapping("/{categoryNo}/child-categories")
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryListResponseDto>>>
    categoryChildList(@PathVariable Integer categoryNo, @PageableDefault Pageable pageable) {

        Page<CategoryListResponseDto> categoryListAboutChild =
            categoryService.findCategoryListAboutChild(categoryNo, pageable);

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_CHILD_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
            new PageResponse<>(categoryListAboutChild)));
    }

    /**
     * 카테고리 세부정보 조회 요청을 받고 반환하는 메서드입니다.
     *
     * @param categoryNo 세부조회할 카테고리의 번호입니다.
     * @return 카테고리의 세부정보를 ResponseEntity에 담아 반환합니다.
     * @author 최겸준 *
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<CategoryDetailsResponseDto>> categoryDetails(
        @PathVariable Integer categoryNo) {

        CommonResponseBody<CategoryDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_DETAILS_SUCCESS_MESSAGE.getSuccessMessage()),
                categoryService.findCategoryDetailsResponseDto(categoryNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 카테고리 수정요청시 요청을 받고 반환하는 역할을 담당합니다.
     *
     * @param categoryNo         수정해야할 카테고리 번호입니다.
     * @param categoryRequestDto the category request dto
     * @return 성공여부 및 기본적인 반환정보를 클라이언트에게 반환합니다. 성공시 204
     * @author 최겸준 *
     */
    @PutMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<Void>> categoryModify(
        @PathVariable Integer categoryNo,
        @Valid @RequestBody CategoryModifyRequestDto categoryRequestDto) {

        categoryService.modifyCategory(categoryNo, categoryRequestDto);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    /**
     * Child category sequence modify response entity.
     *
     * @param categoryNo               the category no
     * @param hopingPositionCategoryNo the hoping position category no
     * @return the response entity
     * @author 정재원 *
     */
    @PutMapping("/{categoryNo}/child-sequence")
    public ResponseEntity<CommonResponseBody<Void>> childCategorySequenceModify(
        @PathVariable Integer categoryNo,
        @RequestParam Integer hopingPositionCategoryNo) {

        categoryService.modifyChildSequence(categoryNo, hopingPositionCategoryNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    /**
     * Main category sequence modify response entity.
     *
     * @param categoryNo the category no
     * @param sequence   the sequence
     * @return the response entity
     * @author 정재원 *
     */
    @PutMapping("/{categoryNo}/main-sequence")
    public ResponseEntity<CommonResponseBody<Void>> mainCategorySequenceModify(
        @PathVariable Integer categoryNo,
        @RequestParam @Min(value = 1, message = "순서는 최솟값이 1입니다.") Integer sequence) {

        categoryService.modifyMainSequence(categoryNo, sequence);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    /**
     * Category modify hidden response entity.
     *
     * @param categoryNo the category no
     * @return the response entity
     * @author 정재원 *
     */
    @PutMapping("/{categoryNo}/hidden")
    public ResponseEntity<CommonResponseBody<Void>> categoryModifyHidden(
        @PathVariable Integer categoryNo) {

        categoryService.modifyCategoryHidden(categoryNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

    /**
     * 카테고리 삭제요청을 처리하는 기능입니다.
     *
     * @param categoryNo 삭제 해야하는 카테고리의 번호입니다.
     * @return 카테고리 삭제여부 등의 갑을 넣어 클라이언트에게 반환합니다.
     * @author 최겸준 *
     */
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<Void>> categoryRemove(
        @PathVariable Integer categoryNo) {

        categoryService.removeCategory(categoryNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_REMOVE_SUCCESS.getSuccessMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK)
            .body(commonResponseBody);
    }
}
