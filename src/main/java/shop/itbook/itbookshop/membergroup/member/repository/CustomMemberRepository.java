package shop.itbook.itbookshop.membergroup.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * query dsl을 사용하기 위한 커스텀 memberRepository 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomMemberRepository {

    /**
     * memberNo로 특정 멤버 정보를 찾는 메서드입니다.
     *
     * @param id member id로 테이블에서 멤버를 찾습니다.
     * @return Dto로 받아와 반환합니다.
     * @author 노수연
     */
    Optional<MemberResponseProjectionDto> querydslFindById(String id);

    //Optional<MemberResponseDto> querydslFindByIdAllInfo(String id);

    Optional<Member> querydslFindByIdToMember(String id);

    /**
     * 모든 회원 리스트를 가져오는 메서드입니다.
     *
     * @return 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    List<MemberResponseProjectionDto> querydslFindAll();

}
