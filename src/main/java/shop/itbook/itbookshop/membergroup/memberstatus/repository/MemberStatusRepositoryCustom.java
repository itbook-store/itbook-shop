package shop.itbook.itbookshop.membergroup.memberstatus.repository;

import java.util.List;
import java.util.Optional;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;

/**
 * query dsl을 사용하기 위한 커스텀 memberStatusRepository 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusRepositoryCustom {

    Optional<MemberStatusResponseDto> querydslFindByName(String memberStatusName);

    List<MemberStatusResponseDto> querydslFindAll();
}
