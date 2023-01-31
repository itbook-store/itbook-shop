package shop.itbook.itbookshop.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * 관리자 카테고리 기능과 관련한 비지니스 로직을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryService {

    /**
     * 카테고리 저장의 비지니스 로직을 처리하는 메서드입니다.
     *
     * @param categoryRequestDto 카테고리 저장에 필요한 정보를 담고 있는 객체입니다.
     * @return 저장한 카테고리의 번호를 반환합니다.
     * @author 최겸준
     */
    Integer addCategory(CategoryRequestDto categoryRequestDto);

    /**
     * 모든 카테고리 조회의 비지니스 로직을 처리하는 메서드입니다.
     *
     * @return 모든 카테고리를 리스트에 담아서 반환합니다.
     * @author 최겸준
     */
    Page<CategoryListResponseDto> findCategoryListByEmployee(Pageable pageable);

    Page<CategoryListResponseDto> findCategoryListByNotEmployee(Pageable pageable);


    /**
     * 특정 카테고리 번호를 받아서 자식 카테고리들의 정보반환 처리를 담당하는 메서드입니다.
     *
     * @param categoryNo 부모 카테고리의 번호입니다.
     * @return 조건에 해당하는 모든 카테고리를 리스트에 담아서 반환합니다.
     * @author 최겸준
     */
    Page<CategoryListResponseDto> findCategoryListAboutChild(Integer categoryNo, Pageable pageable);

    /**
     * 카테고리 엔티티를 찾고 존재한다면 카테고리를 반환하고 존재하지 않는다면 예외를 발생시키는 기능을 담당합니다.
     *
     * @param categoryNo 찾을 카테고리의 번호입니다.
     * @return 카테고리 entity를 반환합니다.
     * @author 최겸준
     */
    Category findCategoryEntity(Integer categoryNo);

    /**
     * 카테고리의 상위카테고리까지 조인하여 가져온 엔티티를 반환하며 존재하지 않을경우 예외를 발생시키는 기능을 담당합니다.
     *
     * @param categoryNo 찾을 카테고리의 번호입니다.
     * @return 카테고리 entity를 반환합니다.
     * @author 최겸준
     */
    Category findCategoryEntityFetch(Integer categoryNo);

    /**
     * 특정 카테고리의 정보를 반환하는 비지니스 로직을 담당하는 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리의 번호입니다.
     * @return 카테고리의 세부정보를 DTO에 담아 반환합니다.
     * @author 최겸준
     */
    CategoryDetailsResponseDto findCategoryDetailsResponseDto(
        Integer categoryNo);

    /**
     * 카테고리 수정에 관한 비즈니스 로직을 담당하는 기능입니다.
     *
     * @param categoryNo         수정해야할 카테고리 번호입니다.
     * @param categoryRequestDto 카테고리 수정에 대한 내용을 담고있는 객체입니다.
     * @return true 혹은 false 값을 반환합니다.
     * @author 최겸준
     */
    void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto);

    void modifyChildSequence(Integer categoryNo, Integer hopingPositionCategoryNo);

    void modifyMainSequence(Integer categoryNo, Integer sequence);

    void modifyCategoryHidden(Integer categoryNo);

    /**
     * 카테고리 삭제에 관한 비지니스 로직을 담당하는 기능입니다.
     *
     * @param categoryNo 삭제해야할 카테고리의 번호입니다.
     * @author 최겸준
     */
    void removeCategory(Integer categoryNo);

    Page<CategoryListResponseDto> findMainCategoryList(Pageable pageable);
}
