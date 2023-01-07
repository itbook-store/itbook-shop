package shop.itbook.itbookshop.category.dto.response;

/**
 * 모든 카테고리를 반환할때 프로젝션할 용도로 사용될 인터페이스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryResponseProjectionDto {

    Integer getCategoryNo();

    @SuppressWarnings("java:S100")
        // JPA 이름규칙에 의거 메소드 생성
    Integer getParentCategory_categoryNo();

    String getCategoryName();

    boolean getIsHidden();
}
