package shop.itbook.itbookshop.category.dto.response;

/**
 * 특정 부모번호를 통해 자식들을 가져오려할때 프로젝션용으로 사용하는 dto 입니다.
 * 해당 dto 는 단순히 자식들만 가져가면 되기때문에 부모번호가 필요없습니다. 이 DTO를 반환받는 곳에서는 이미 부모번호가 있습니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryChildResponseProjectionDto {

    Integer getCategoryNo();

    String getCategoryName();

    boolean getIsHidden();
}
