package shop.itbook.itbookshop.category.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.category.service.CategoryService;
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

    private final CategoryService categoryService;

    /**
     * 카테고리 저장 요청을 처리하는 메서드입니다.
     *
     * @param categoryRequestDto 저장을 위한 정보를 바인딩받는 객체입니다.
     * @return 저장한 카테고리의 번호를 ResponseEntity 에 담아 반환합니다
     * @author 최겸준
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
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>> categoryList() {

        CommonResponseBody<List<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                categoryService.findCategoryListByEmployee());

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/main-categories")
    public ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>> mainCategoryList() {

        CommonResponseBody<List<CategoryListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                categoryService.findMainCategoryList());

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 부모카테고리의 번호를 받아서 해당 카테고리의 자식카테고리들을 반환하는 요청을 처리하는 메서드입니다.
     *
     * @param categoryNo 부모카테고리의 번호입니다.
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/{categoryNo}/child-categories")
    public ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>>
    categoryChildList(@PathVariable Integer categoryNo) {

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CategoryResultMessageEnum.CATEGORY_CHILD_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
            categoryService.findCategoryListAboutChild(categoryNo)));
    }

    /**
     * 카테고리 세부정보 조회 요청을 받고 반환하는 메서드입니다.
     *
     * @param categoryNo 세부조회할 카테고리의 번호입니다.
     * @return 카테고리의 세부정보를 ResponseEntity에 담아 반환합니다.
     * @author 최겸준
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
     * @param categoryNo 수정해야할 카테고리 번호입니다.
     * @return 성공여부 및 기본적인 반환정보를 클라이언트에게 반환합니다. 성공시 204
     * @author 최겸준
     */
    @PutMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<Void>> categoryModify(
        @PathVariable Integer categoryNo, @RequestBody CategoryRequestDto categoryRequestDto) {

        categoryService.modifyCategory(categoryNo, categoryRequestDto);

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
     * @author 최겸준
     */
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<CommonResponseBody<Void>> categoryRemove(
        @PathVariable Integer categoryNo) {

        categoryService.removeCategory(categoryNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CategoryResultMessageEnum.CATEGORY_REMOVE_SUCCESS.getSuccessMessage()), null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
            .body(commonResponseBody);
    }
}
