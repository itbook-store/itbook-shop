package shop.itbook.itbookshop.category.controller.adminapi;

import java.util.List;
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
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;

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
    public ResponseEntity<CategoryNoResponseDto> categorySave(
        @RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryNoResponseDto dto = new CategoryNoResponseDto();
        dto.setCategoryNo(categoryAdminService.saveCategory(categoryRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dto);
    }

    /**
     * 카테고리의 모든 리스트를 조회하는 메서드입니다.
     *
     * @return 카테고리정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseProjectionDto>> categoryList() {

        return ResponseEntity.ok().body(categoryAdminService.findCategoryList());
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
    public ResponseEntity<List<CategoryResponseProjectionDto>> categoryChildList(
        @PathVariable Integer categoryNo) {

        return ResponseEntity.ok().body(categoryAdminService.findCategoryChildList(categoryNo));
    }

    /**
     * 카테고리 세부정보 조회 요청을 받고 반환하는 메서드입니다.
     *
     * @param categoryNo 세부조회할 카테고리의 번호입니다.
     * @return 카테고리의 세부정보를 ResponseEntity에 담아 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDto> categoryDetails(@PathVariable Integer categoryNo) {

        return ResponseEntity.ok().body(categoryAdminService.findCategory(categoryNo));
    }
}
