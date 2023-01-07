package shop.itbook.itbookshop.category.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
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

    @PostMapping
    public ResponseEntity<CategoryNoResponseDto> categorySave(
        CategoryRequestDto categoryRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryAdminService.saveCategory(categoryRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> categoryList() {

        return ResponseEntity.ok().body(categoryAdminService.findCategoryList());
    }

    @GetMapping("/{categoryNo}/child-categories")
    public ResponseEntity<List<CategoryResponseDto>> categoryChildList(
        @PathVariable String categoryNo) {

        return ResponseEntity.ok().body(categoryAdminService.findCategoryChildList(categoryNo));
    }

    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDto> categoryDetails(
        @PathVariable String categoryNo) {

        return ResponseEntity.ok().body(categoryAdminService.findCategory(categoryNo));
    }
}
